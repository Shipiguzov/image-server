import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ifmo.imageserver.entity.AdditionalImageInfo;
import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.entity.Image;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ToJson {
    public static void main(String[] args) {

        Author author = new Author("Nickname");
        Image image = new Image("testimage.jpg", author);
        AdditionalImageInfo info = new AdditionalImageInfo();
        author.setBirthdate(LocalDate.of(1984, 5, 7));
        author.setCity("Saint-Petersburg");
        author.addImage(image);
        author.setCountry("Russia");
        author.setName("Ivan");
        author.setSurname("Petrov");
        info.setCity("Berlin");
        info.setCountry("Gemany");
        info.setDate(LocalDateTime.now().minusDays(7));
        image.setFileName();
        image.setAdditionalImageInfo(info);
        author.addImage(image);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        try {
            writer.writeValue(new File("C:/Java/image.txt"), image);
            writer.writeValue(new File("C:/Java/author.txt"), author);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
