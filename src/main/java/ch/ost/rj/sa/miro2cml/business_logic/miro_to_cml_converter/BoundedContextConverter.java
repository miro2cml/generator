package ch.ost.rj.sa.miro2cml.business_logic.miro_to_cml_converter;

import ch.ost.rj.sa.miro2cml.business_logic.StringValidator;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingLog;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingMessages;
import ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation.BoundedContext;
import ch.ost.rj.sa.miro2cml.business_logic.model.miorboard_representation.BoundedContextBoard;

import javax.print.DocFlavor;
import java.util.ArrayList;

public class BoundedContextConverter {
    public static BoundedContext convertExtractedBoardToCMLBoundedContext(BoundedContextBoard extractedBoard, MappingLog mappingLog, MappingMessages messages){
        return new BoundedContext(generateComment(extractedBoard), generateName(extractedBoard.getName(), mappingLog, messages),
                generateDescription(extractedBoard.getDescription(), mappingLog, messages),
                generateAggregateName(generateName(extractedBoard.getName(), mappingLog, messages), extractedBoard.getAggregateName()),
                generateResponsibilities(extractedBoard.getRoleTypes()),
                generateDomainEvents(extractedBoard.getEvents()),
                generateQueries(extractedBoard.getQueries()),
                generateCommands(extractedBoard.getCommands()),
                mappingLog, messages);
    }

    private static ArrayList<String> generateCommands(ArrayList<String> commands) {
        return convertArrays(commands);
    }

    private static ArrayList<String> generateQueries(ArrayList<String> queries) {
        return convertArrays(queries);
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
        ArrayList<String> responsibilites = new ArrayList<>();
        var roletype = roletypes.split("</p><p>");
        for (String s: roletype) {
            responsibilites.add(StringValidator.validatorForStrings(s));
        }
        //remove heading from responsibilities list
        responsibilites.remove(0);
        return responsibilites;
    }

    private static String generateAggregateName(String name, String aggregateName) {
        return StringValidator.convertForVariableName(name+aggregateName);
    }

    private static String generateDescription(String description, MappingLog mappingLog, MappingMessages messages) {
        if(description==null || description.equals("")){
            mappingLog.addErrorLogEntry("Description is empty");
            messages.add("Description not found. Check if you have set a description under the description tag");
            description="This is the Domain Vision Statement";
            mappingLog.addInfoLogEntry("Automatic Domain Vision Statement set to: This is the Domain Vision Statement");
        }
        return StringValidator.validatorForStrings(description);
    }

    private static String generateComment(BoundedContextBoard extractedBoard) {
        return "/** Strategic Classifications: \n *"+
                validateStringsForComment(extractedBoard.getDomain()) + "\n *"
                + validateStringsForComment(extractedBoard.getBusinessModel())+ "\n *"
                + validateStringsForComment(extractedBoard.getEvolution()) + "\n *" +
                "Ubiquitous Language: " + validateArrays(extractedBoard.getUbiquitousLanguage()) + "\n *" +
                "Business Descisions: " + validateArrays(extractedBoard.getBusinessDescisions()) + "\n *" +
                "Outbound Communications: " + validateArrays(extractedBoard.getOutBoundCommunication()) +
                "\n */";
    }

    private static String validateArrays(ArrayList<String> input) {
        String output="";
        if(!input.isEmpty()){
            for(String s: input){
                if(s != null && !s.equals("")){
                    output = output + s + ", ";
                }
            }
            if(output.length()>2){
                output = output.substring(0, output.length()-2);
            }
        }
        return StringValidator.validatorForStrings(output);
    }

    private static String validateStringsForComment(String input) {
        input = StringValidator.replaceLineWithComma(input);
        input= input.replaceFirst(",", ":");
        return StringValidator.validatorForStrings(input);
    }

    private static String generateName(String name, MappingLog mappingLog, MappingMessages messages) {
        String generatedName = removeTemplateText(name, mappingLog, messages);
        return StringValidator.convertForVariableName(generatedName);
    }

    private static String removeTemplateText(String name, MappingLog mappingLog, MappingMessages messages) {
        String generatedName = StringValidator.removeSimpleHtmlTags(name);
        if(generatedName.length()>7){
            if(generatedName.contains("Name: ")){
                generatedName = generatedName.substring(6);
            }else{
                generatedName=generatedName.substring(5);
            }
        }else{
            generatedName="MyBoundedContext";
            if(!(mappingLog.getLogEntries().contains("ERROR: Name not found"))){
                messages.add("Name not found. Check if you have set a Name at the field Name: ");
                mappingLog.addErrorLogEntry("Name not found");
                mappingLog.addInfoLogEntry("Name set to MyBoundedContext");
            }
        }
        return StringValidator.validatorForStrings(generatedName);

    }
}
