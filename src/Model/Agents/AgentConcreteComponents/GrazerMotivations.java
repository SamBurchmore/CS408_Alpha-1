//package Model.Agents.AgentConcreteComponents;
//
//import Model.Agents.AgentInterfaces.Attributes;
//import Model.Agents.AgentInterfaces.Motivations;
//import Model.Agents.AgentInterfaces.Scores;
//import Model.Agents.AgentStructs.AgentVision;
//import Model.Environment.EnvironmentTile;
//
//public class GrazerMotivations implements Motivations {
//
//    @Override
//    public int run(AgentVision tile, Attributes attributes, Scores scores) {
//        if (tile.getFoodLevel() >= attributes.getEatAmount()) {
//            return 5;
//        }
//        if (tile.getFoodLevel() > 0) {
//            return 3;
//        }
//        return 0;
//    }
//
//}
