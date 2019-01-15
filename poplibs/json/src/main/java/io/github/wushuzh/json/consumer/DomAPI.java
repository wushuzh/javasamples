package io.github.wushuzh.json.consumer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wushuzh.json.pojo.ExampleLoan;
import io.github.wushuzh.json.producer.GenAPI;

public class DomAPI {

  public static void main(String[] args) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(GenAPI.toJsonString(ExampleLoan.LOAN_APPLICATION));

    // System.out.println(jsonNode);

    // ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    // System.out.println(objectWriter.writeValueAsString(jsonNode));

    validateDates(jsonNode);
  }

  private static void validateDates(final JsonNode jsonNode) {
    Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
    while (fields.hasNext()) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode childNode = field.getValue();
      if (childNode.isTextual() && fieldName.endsWith("Date")) {
        System.out.println("Found date field: " + fieldName);
        String fieldValue = childNode.asText();
        try {
          DateTimeFormatter.ISO_LOCAL_DATE.parse(fieldValue);
        } catch (DateTimeException e) {
          System.out.println("Invalid value: " + fieldValue);
        }
      } else {
        validateDates(childNode);
      }
    }
  }

}
