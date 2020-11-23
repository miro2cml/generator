package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.BoundedContext;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.BoundedContextBoard;

import java.util.ArrayList;

public class BoundedContextConverter {
    public static BoundedContext convertExtractedBoardToCMLBoundedContext(BoundedContextBoard extractedBoard){
        return new BoundedContext(generateComment(extractedBoard), generateName(extractedBoard.getName()),
                generateDescription(extractedBoard.getDescription()),
                generateAggregateName(generateName(extractedBoard.getName()), extractedBoard.getAggregateName()),
                generateResponsibilities(extractedBoard.getRoleTypes()),
                generateDomainEvents(extractedBoard.getEvents()),
                generateOperations(extractedBoard.getQueries(), extractedBoard.getCommands()));
    }

    private static ArrayList<String> generateOperations(ArrayList<String> queries, ArrayList<String> commands) {
        ArrayList<String> operations = convertArrays(queries);
        operations.addAll(convertArrays(commands));
        return operations;
    }

    private static ArrayList<String> generateDomainEvents(ArrayList<String> events) {
        return convertArrays(events);
    }

    private static ArrayList<String> convertArrays(ArrayList<String> input) {
        ArrayList<String> output = new ArrayList<>();
        for(String s: input){
            if(!s.equals("")){
                output.add(StringValidator.convertForVariableName(s));
            }
        }
        return output;
    }

    private static ArrayList<String> generateResponsibilities(String roletypes) {
        ArrayList<String> responsisbilites = new ArrayList<>();
        roletypes = StringValidator.validatorForStrings(roletypes);
        responsisbilites.add(roletypes);
        return responsisbilites;
    }

    private static String generateAggregateName(String name, String aggregateName) {
        return StringValidator.convertForVariableName(name+aggregateName);
    }

    private static String generateDescription(String description) {
        return StringValidator.validatorForStrings(description);
    }

    private static String generateComment(BoundedContextBoard extractedBoard) {
        return "/* Strategic Classifications: "+
                validateStringsForComment(extractedBoard.getDomain()) + "/"
                + validateStringsForComment(extractedBoard.getBusinessModel())+ "/"
                + validateStringsForComment(extractedBoard.getEvolution()) + "/" +
                "Ubiquitous Language: " + validateArrays(extractedBoard.getUbiquitousLanguage()) + "/" +
                "Business Descisions: " + validateArrays(extractedBoard.getBusinessDescisions()) + "/" +
                "Outbound Communications: " + validateArrays(extractedBoard.getOutBoundCommunication()) +
                "*/";
    }

    private static String validateArrays(ArrayList<String> input) {
        String output="";
        for(String s: input){
            if(!s.equals("")){
                output= output +", "+ s;
            }
        }
        return StringValidator.validatorForStrings(output);
    }

    private static String validateStringsForComment(String input) {
        input = StringValidator.replaceLineWithComma(input);
        return StringValidator.validatorForStrings(input);
    }

    private static String generateName(String name) {
        String generatedName = removeTemplateText(name);
        return StringValidator.convertForVariableName(generatedName);
    }

    private static String removeTemplateText(String name) {
        String generatedName = StringValidator.removeSimpleHtmlTags(name);
        if(generatedName.contains("Name: ")){
            generatedName = generatedName.substring(6);
        }else{
            generatedName=generatedName.substring(5);
        }
        return generatedName;
    }
}
