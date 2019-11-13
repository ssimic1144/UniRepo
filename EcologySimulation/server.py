from model import SimModel, Food, PrimaryConsumer, SecondaryConsumer, Water
from mesa.visualization.modules import CanvasGrid
from mesa.visualization.ModularVisualization import ModularServer
from mesa.visualization.modules import ChartModule, BarChartModule, PieChartModule


def agent_portrayal(agent):
    
    portrayal = {"Shape":"circle", "Filled":"true", "r":0.5}
    
    if type(agent) == Food:
        portrayal["Color"] = "green"
        portrayal["Layer"] = 0
    elif type(agent) == PrimaryConsumer:
        portrayal["Color"] = "red"
        portrayal["Layer"] = 1
    elif type(agent) == SecondaryConsumer:
        portrayal["Color"] = "black"
        portrayal["Layer"] = 1
    elif type(agent) == Water:
        portrayal["Color"] = "blue"
        portrayal["Layer"] = 2

    
    return portrayal

#Creates grid with agents on 30x30 grid with 500x500 pix
grid = CanvasGrid(agent_portrayal, 30, 30, 500,500)

#Create chart for visualization on Food and Predator population
chart = ChartModule([{"Label":"Food","Color":"green"},{"Label":"Primary Consumer", "Color":"red"}, {"Label":"Secondary Consumer", "Color":"black"}],data_collector_name="datacollector")
chart2 = PieChartModule([{"Label":"Male Primary consumers","Color":"blue"},{"Label":"Female Primary consumers","Color":"yellow"}], data_collector_name="datacollector2", canvas_height=250, canvas_width=250)
chart3 = BarChartModule([{"Label":"Primary Consumer natural death","Color":"brown"},{"Label":"Primary Consumer death in combat","Color":"pink"}], data_collector_name="datacollector3",canvas_height=300, canvas_width=600)
chart4 = PieChartModule([{"Label":"Male Secondary consumers","Color":"darkgreen"},{"Label":"Female Secondary consumers","Color":"darkgrey"}], data_collector_name="datacollector4", canvas_height=250, canvas_width=250)
chart5 = BarChartModule([{"Label":"Natural death","Color":"#eb7f03"},{"Label":"Death in combat","Color":"#642e6e"}], data_collector_name="datacollector5", canvas_height=300, canvas_width=600)

chart6 = ChartModule([{"Label":"Mean Primary Life","Color":"#940202"},{"Label":"Mean Secondary Life","Color":"#000003"},{"Label":"Primary Life standard deviation","Color":"#ffabdc"},{"Label":"Secondary Life standard deviation","Color":"#5f5f61"}], data_collector_name="datacollector6")

server = ModularServer(SimModel, [grid,chart,chart2, chart4, chart3, chart5, chart6], "Ecosystem Model", {"N":89,"M":100, "O":44, "W":1,"width":30, "height":30})
server.port = 8521