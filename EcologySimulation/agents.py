from mesa import Agent


class Food(Agent):
    """Photosynthetic Agent """
    def __init__(self, unqId, model,pos):
        super().__init__(unqId, model)
        self.energy = 1
        self.pos = pos

    def step(self):
        """Agent step, what agent does"""
        print("Food agent id", self.unique_id, " activated, position : ",self.pos, " with energy : ", self.energy)
        if self.energy < 2:
            print("Food agent id ",self.unique_id," get energy")
            self.energy += 0.65
        elif self.energy >= 2 :
            print("Food agent id ",self.unique_id, " reproduce") 
            self.foodReproduction()

    def foodReproduction(self):
        """Food grows in any free cell around it"""
        #Get cells around agent
        food_surroundings = self.model.grid.get_neighborhood(self.pos, moore=True, include_center=False)
        emptyCell = ()
        #Take first empty cell
        for i in food_surroundings:
            if len(self.model.grid.get_cell_list_contents([i])) == 0:
                emptyCell = i
        if emptyCell:
            print("Empty cell at ", emptyCell)
            #Grow new food
            newFood = Food(self.model.next_id(),self.model, emptyCell)
            self.model.schedule.add(newFood)
            self.model.grid.place_agent(newFood, emptyCell)
            self.energy /= 2
        else:
            print("No empty cell, can't grow new food")
            #If no space to reproduce then it dies
            self.model.grid._remove_agent(self.pos, self)
            self.model.schedule.remove(self)
            return


def consumerReproduction(activeAgent, secondAgent, className, pasivity_rate):
    """Universal function for consumers reproduction"""
    if (type(secondAgent) == className) and (secondAgent.unique_id != activeAgent.unique_id):
        print(className.__name__, secondAgent.sex)
        if secondAgent.sex != activeAgent.sex:
            if (activeAgent.passiveCounter <= 0 and secondAgent.passiveCounter <= 0):
                #Set passive counters
                activeAgent.passiveCounter += pasivity_rate
                secondAgent.passiveCounter += pasivity_rate
                #Energy reduction
                activeAgent.energy -= 0.2
                secondAgent.energy -= 0.2
                #Then it reproduces
                print("Agent ",className.__name__, " id ",activeAgent.unique_id, activeAgent.sex," reproduces with agent ", secondAgent.unique_id, secondAgent.sex)
                newAgent = className(activeAgent.model.next_id(), activeAgent.model, activeAgent.pos, sex = activeAgent.random.choice(["M","F"]),justBorn=True, passiveCounter=pasivity_rate)
                activeAgent.model.schedule.add(newAgent)
                activeAgent.model.grid.place_agent(newAgent, activeAgent.pos)
                print(newAgent.__class__.__name__, newAgent.unique_id," was born")
            else:
                print("One or more agents are in passive")

def consumerEatsFood(activeAgent, secondAgent):
    """ Universal function for consumers eatting Food"""
    if type(secondAgent) == Food:
        print( activeAgent.__class__.__name__ ," agent eats Food id ", secondAgent.unique_id)
        #Eat food
        activeAgent.model.grid._remove_agent(activeAgent.pos, secondAgent)
        activeAgent.model.schedule.remove(secondAgent)
        activeAgent.energy += 0.4

def consumerStepBegining(activeAgent, dictName):
    """ Universal begining of each step phase for consumers """
    print(activeAgent.__class__.__name__," agent id", activeAgent.unique_id, " activated, position : ",activeAgent.pos,
          " just born : ", activeAgent.justBorn ," with energy : ", activeAgent.energy,
          " and passive counter of : ", activeAgent.passiveCounter)
    activeAgent.energy -= 0.1
    if activeAgent.energy < 0:
        print(activeAgent.__class__.__name__," id ", activeAgent.unique_id," dies")
        if activeAgent.energy < 0:
            dictName["Natural"].append(activeAgent.unique_id)
        activeAgent.alive = False
        return  activeAgent
    if activeAgent.passiveCounter > 0:
        activeAgent.passiveCounter -= 0.8

def consumerRandomMovement(activeAgent, moore=True):
    """Move agent in one of posible directions"""
    previous_location = activeAgent.pos
    possible_steps = activeAgent.model.grid.get_neighborhood(previous_location, moore=moore, include_center=False)

    new_position = activeAgent.random.choice(possible_steps)        

    cellmates = activeAgent.model.grid.get_cell_list_contents([new_position])
    cell_has_water = [x for x in cellmates if type(x) == Water]

    while len(cell_has_water) > 0:
        print("Cell has water finding new cell")
        possible_steps.remove(new_position)
        if len(possible_steps) == 0:
            print("Water everywhere, can't move, agent drowns")
            activeAgent.alive = False
            return

        new_position = activeAgent.random.choice(possible_steps)
        cellmates = activeAgent.model.grid.get_cell_list_contents([new_position])
        cell_has_water = [x for x in cellmates if type(x) == Water]
        print("Cells left",len(possible_steps))
        print("Water agents in new cell ", len(cell_has_water))
        
        


    activeAgent.model.grid.move_agent(activeAgent, new_position)
    print(activeAgent.__class__.__name__ ," agent id ",activeAgent.unique_id," moved from ", previous_location," to ", new_position)



class PrimaryConsumer(Agent):

    life_duration = 0

    """Agent who eats the Food"""
    def __init__(self, unqId, model, pos, sex, justBorn=False, passiveCounter=0):
        super().__init__(unqId, model)
        self.energy = 0.7
        self.pos = pos
        self.sex = sex
        self.justBorn = justBorn
        self.passiveCounter = passiveCounter
        self.alive=True
        self.pasivity_rate = 3

    def step(self):
        """Agent finding food or mate"""
        
        self.life_duration += 1

        consumerStepBegining(self, dictName= self.model.primary_deaths_dict)

        if not(self.alive):
            #If agent's dead remove it
            self.model.grid._remove_agent(self.pos, self)
            self.model.schedule.remove(self)
            return

        if self.justBorn:
            #If agent's just born this tick agent only moves
            consumerRandomMovement(self)
            self.justBorn = False
            return

        consumerRandomMovement(self)
        if not(self.alive):
            #If agent's dead remove it
            self.model.grid._remove_agent(self.pos, self)
            self.model.schedule.remove(self)
            return
        self.feed_reproduce()


    def feed_reproduce(self):
        """Agent tries to eat/reproduce"""
    
        cellmates = self.model.grid.get_cell_list_contents([self.pos])
        if len(cellmates) > 1:
            print("This cell has these agents ", cellmates)
            for i in range(len(cellmates)):
                
                #Eatting food
                consumerEatsFood(self, cellmates[i])
                #Reproduction 
                consumerReproduction(self, cellmates[i], PrimaryConsumer, self.pasivity_rate)
        else:
            print("No cellmates")
            self.energy += 0.05



class SecondaryConsumer(Agent):
    """ Agent who eats Primary Consumers and fight with each other"""

    life_duration = 0

    def __init__(self, unqId, model, pos, sex, justBorn=False, passiveCounter=0):
        super().__init__(unqId, model)
        self.energy = 10
        self.pos = pos
        self.sex = sex
        self.justBorn = justBorn
        self.passiveCounter = passiveCounter
        self.alive = True
        self.pasivity_rate = 3.4


    
    def step(self):
        "Agent ineracting with enviroment"
        self.life_duration += 1
        consumerStepBegining(self, dictName=self.model.secondary_deaths_dict)    
        if not(self.alive):
            self.model.grid._remove_agent(self.pos, self)
            self.model.schedule.remove(self)
            return
        if self.justBorn:
            consumerRandomMovement(self)
            self.justBorn = False
            return
        consumerRandomMovement(self)
        if not(self.alive):
            #If agent's dead remove it
            self.model.grid._remove_agent(self.pos, self)
            self.model.schedule.remove(self)
            return

        self.feed_reproduce_attack()
        if not(self.alive):
            return
        


    def feed_reproduce_attack(self):
        cellmates = self.model.grid.get_cell_list_contents([self.pos])
        if len(cellmates) > 1:
            print("This cell has these agents ", cellmates)
            for i in range(len(cellmates)):
                #Reproduction 
                consumerReproduction(self, cellmates[i], SecondaryConsumer, self.pasivity_rate)
                if type(cellmates[i]) == PrimaryConsumer:
                    if self.energy > 10:
                        break
                    #Secondary consumer agent attacks other 
                    cellmates[i].energy -= 1.5
                    print("PrimaryConsumer id ", cellmates[i].unique_id, "takes damage from Secondary consumer id ",self.unique_id)
                    self.energy -= 0.1
                    if self.energy < 0:
                        print("Secondary Consumer id ",self.unique_id," died in combat")
                        self.model.secondary_deaths_dict["Combat"].append(self.unique_id)
                        self.model.grid._remove_agent(self.pos, self)
                        self.model.schedule.remove(self)
                        self.alive = False
                        return
                    if cellmates[i].energy <= 0:
                        #SecondaryConsumer agent kills other predator
                        print("PrimaryConsumer id ",cellmates[i].unique_id," died from attack")
                        #Eat primary consumer
                        self.energy += 6
                        #Add dead agent to dictionary
                        self.model.primary_deaths_dict["Combat"].append(cellmates[i].unique_id)
                        self.model.grid._remove_agent(self.pos, cellmates[i])
                        self.model.schedule.remove(cellmates[i])
                if type(cellmates[i]) == SecondaryConsumer and self.sex == cellmates[i].sex and cellmates[i].unique_id != self.unique_id:
                    #If same species fight
                    print("Secondary Consumer id ", cellmates[i].unique_id, "takes damage from Secondary consumer id ",self.unique_id)                    
                    cellmates[i].energy -= 5
                    self.energy -= 2
                    if self.energy < 0:
                        print("Secondary Consumer id ",self.unique_id," died in combat")
                        self.model.secondary_deaths_dict["Combat"].append(self.unique_id)
                        self.model.grid._remove_agent(self.pos, self)
                        self.model.schedule.remove(self)
                        return
                    if cellmates[i].energy <= 0:
                        print("Secondary Consumer id ",cellmates[i].unique_id," died from attack")
                        #Add dead agent to dictionary
                        self.model.secondary_deaths_dict["Combat"].append(cellmates[i].unique_id)
                        self.model.grid._remove_agent(self.pos, cellmates[i])
                        self.model.schedule.remove(cellmates[i])
        else:
            print("No cellmates")
            self.energy += 0.05




class Water(Agent):
    """ Agent who servers as a natural barrier """

    def __init__(self,unqId, model,pos, timer):
        super().__init__(unqId, model)
        self.pos = pos
    
        self.timer = timer

    def step(self):
        pass
    def move(self):
        """ How would water agent move """
        pass

    def scanArea(self):
        counter = 0
        area = self.model.grid.get_neighborhood(self.pos, moore=False, include_center=False)
        for cell in area:
            population = self.model.grid.get_cell_list_contents([cell])
            for each in population:
                if type(each) == Water and each.unique_id != self.unique_id:
                    counter +=1
        if counter == 0:
            self.energy = 0

    
    def takeLand(self):
       pass