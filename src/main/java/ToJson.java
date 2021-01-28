import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.entity.Image;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ToJson {
    public static void main(String[] args) {

        Author author = new Author("Nickname");
        author.setId(4);
        Image image = new Image("testimage.jpg", author);
        author.setBirthdate(LocalDate.of(1984, 5, 7));
        author.setCity("Saint-Petersburg");
        author.setCountry("Russia");
        author.setName("Ivan");
        author.setSurname("Petrov");
        image.setCity("Berlin");
        image.setCountry("Gemany");
        image.setDate(LocalDateTime.now().minusDays(7));
        image.setFileName();
        byte[] bytes = {1, 0};
        image.setByteArray(bytes);
        author.addImage(image);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        try {
            writer.writeValue(new File("C:/Java/image.txt"), image);
            writer.writeValue(new File("C:/Java/author.txt"), author);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Author authorFromJson = new Author();
        Image imageFromJson = new Image();
        try {
            authorFromJson = mapper.readValue(new File("C:/Java/author.txt"), Author.class);
            imageFromJson = mapper.readValue(new File("C:/Java/image.txt"), Image.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(author.equals(authorFromJson));
        System.out.println(image.equals(imageFromJson));
    }
}
