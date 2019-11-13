from agents import PrimaryConsumer,Food, SecondaryConsumer, Water

import numpy as np
from scipy import stats

import matplotlib.pyplot as plt

from mesa import Model
from mesa.time import RandomActivation, BaseScheduler
from mesa.space import MultiGrid
from mesa.datacollection import DataCollector


class SimModel(Model):
    """Model with some number of agents, agent types are Food, Primary Consumer and Secondary Consumer"""
    def __init__(self, N, M, O, W, width, height):
        super().__init__()
        self.number_food = N
        self.number_primaryConsumer = M
        self.number_secondaryConsumer = O
        self.number_water = W
        self.grid = MultiGrid(width, height, True,)
        self.schedule = RandomActivation(self)
        self.natureSchedule = BaseScheduler(self)
        
        #Dictionary for collecting primary agents deaths
        self.primary_deaths_dict = {}
        self.primary_deaths_dict["Natural"] = list()
        self.primary_deaths_dict["Combat"] = list()
        #Dict for collecting born primary and secondary
        self.average_life_primary = {}
        self.average_life_secondary = {}

        #Dictionary for collecting secondary agents deaths
        self.secondary_deaths_dict = {}
        self.secondary_deaths_dict["Natural"] = list()
        self.secondary_deaths_dict["Combat"] = list()


        #Create food
        for i in range(self.number_food):
            #Add agent to grid with x,y cord for position
            x = self.random.randrange(self.grid.width)
            y = self.random.randrange(self.grid.height)
            food = Food(self.next_id(), self, (x,y))
            print("Food agent created id :",food.unique_id)
            self.schedule.add(food)
            self.grid.place_agent(food, (x,y))
        #Create primaryConsumer
        for i in range(self.number_primaryConsumer):
            #Add agent to grid with x,y cord for position
            x = self.random.randrange(self.grid.width)
            y = self.random.randrange(self.grid.height)
            primaryConsumer = PrimaryConsumer(self.next_id(), self, (x,y), sex = self.random.choice(["M","F"]))
            print("Primary consumer agent created id :",primaryConsumer.unique_id, " sex : ", primaryConsumer.sex)
            self.schedule.add(primaryConsumer)
            self.grid.place_agent(primaryConsumer, (x,y))
        
        #Create secondaryConsumer
        for i in range(self.number_secondaryConsumer):
            #Add agent to grid with x,y cord for position
            x = self.random.randrange(self.grid.width)
            y = self.random.randrange(self.grid.height)
            secondaryConsumer = SecondaryConsumer(self.next_id(), self, (x,y), sex = self.random.choice(["M","F"]))
            print("Secondary consumer agent created id :",secondaryConsumer.unique_id, " sex : ", secondaryConsumer.sex)
            self.schedule.add(secondaryConsumer)
            self.grid.place_agent(secondaryConsumer, (x,y))
        
        
        #Create water agent
        for i in range(self.number_water):
            x = int(self.grid.width/2)
            y = self.random.randrange(self.grid.height)

            while x+14 >= self.grid.width or x-14 < 0:
                x = self.random.randrange(self.grid.width)
            while y+2 >= self.grid.height or y-2 < 0:
                y = self.random.randrange(self.grid.height)
                


            for i in range(15):
                #Add water to grid 
                for j in range(3):
                    waterAgent = Water(self.next_id(), self, (x+i,y+j), timer = 0)
                    self.natureSchedule.add(waterAgent)
                    self.grid.place_agent(waterAgent, (x+i,y+j))
                    print("Water agent created id : ",waterAgent.unique_id)
                    waterAgent = Water(self.next_id(), self, (x-i,y-j), timer = 0)
                    self.natureSchedule.add(waterAgent)
                    self.grid.place_agent(waterAgent, (x-i,y-j))
                    print("Water agent created id : ",waterAgent.unique_id)
                    waterAgent = Water(self.next_id(), self, (x+i,y-j), timer = 0)
                    self.natureSchedule.add(waterAgent)
                    self.grid.place_agent(waterAgent, (x+i,y-j))
                    print("Water agent created id : ",waterAgent.unique_id)
                    waterAgent = Water(self.next_id(), self, (x-i,y+j), timer = 0)
                    self.natureSchedule.add(waterAgent)
                    self.grid.place_agent(waterAgent, (x-i,y+j))
                    print("Water agent created id : ",waterAgent.unique_id)

        self.datacollector = DataCollector(
            {"Food": lambda m: m.count_types(self, Food),
             "Primary Consumer": lambda m: m.count_types(self, PrimaryConsumer),
             "Secondary Consumer": lambda m: m.count_types(self, SecondaryConsumer)})

        self.datacollector2 = DataCollector(
            {"Male Primary consumers": lambda t: t.count_agent_sex(self, "M", PrimaryConsumer),
             "Female Primary consumers": lambda t:t.count_agent_sex(self, "F", PrimaryConsumer)}
        )

        self.datacollector3 = DataCollector(
            {"Primary Consumer natural death": lambda z: z.count_agent_death(self.primary_deaths_dict, "Natural"),
             "Primary Consumer death in combat": lambda z: z.count_agent_death(self.primary_deaths_dict, "Combat")}
        )

        self.datacollector4 = DataCollector(
            {"Male Secondary consumers": lambda t: t.count_agent_sex(self, "M", SecondaryConsumer),
             "Female Secondary consumers": lambda t:t.count_agent_sex(self, "F", SecondaryConsumer)}
        )

        self.datacollector5 =  DataCollector(
            {"Natural death": lambda z: z.count_agent_death(self.secondary_deaths_dict, "Natural"),
             "Death in combat": lambda z: z.count_agent_death(self.secondary_deaths_dict, "Combat")}
        )

        self.datacollector6 = DataCollector(
            {"Mean Primary Life": lambda v: v.count_mean_and_std(self, self.average_life_primary, PrimaryConsumer, mode="mean"),
             "Mean Secondary Life": lambda v: v.count_mean_and_std(self, self.average_life_secondary, SecondaryConsumer, mode="mean"),
             "Primary Life standard deviation": lambda v: v.count_mean_and_std(self, self.average_life_primary, PrimaryConsumer, mode="std"),
             "Secondary Life standard deviation": lambda v: v.count_mean_and_std(self, self.average_life_secondary, SecondaryConsumer, mode="std"),
             }
        )


        self.running = True
        self.datacollector.collect(self)
        self.datacollector2.collect(self)
        self.datacollector3.collect(self)
        self.datacollector4.collect(self)
        self.datacollector5.collect(self)
        self.datacollector6.collect(self)

    def step(self):
        """Advance the model by one step"""
        print("Advance the model by one step")
        self.natureSchedule.step()
        self.schedule.step()
        self.datacollector.collect(self)
        self.datacollector2.collect(self)
        self.datacollector3.collect(self)
        self.datacollector4.collect(self)
        self.datacollector5.collect(self)
        self.datacollector6.collect(self)


    
    @staticmethod    
    def count_types(model, className):
        count = 0
        for agent in model.schedule.agents:
            if type(agent) == className:
                count +=1
        return count
    
    @staticmethod
    def count_agent_sex(model, sex, className):
        count = 0
        for agent in model.schedule.agents:
            if type(agent) == className:
                if agent.sex == sex:
                    count += 1
        return count
    
    @staticmethod
    def count_agent_death(dictName, death):
        return len(dictName[death])
    
    @staticmethod
    def count_mean_and_std(model, dictName, className, mode):
        count = list()
        for agent in model.schedule.agents:
            if type(agent) == className:
                dictName[agent.unique_id] = agent.life_duration
                count.append(dictName[agent.unique_id])
        if mode == "mean":
            return np.mean(count)
        if mode == "std":
            return np.std(count)
   