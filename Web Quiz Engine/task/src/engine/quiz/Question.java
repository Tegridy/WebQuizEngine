package engine.quiz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "questions")
@SequenceGenerator(name="seq", initialValue=0, allocationSize = 1)
public class Question {
    @Expose
    @Id

    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "seq")
    private int id;

    @Expose
    @NotBlank
    @Column
    private String title;

    @Expose
    @NotBlank
    @Column
    private String text;

    @Expose
    @NotEmpty
    @Size(min = 2)
    @ElementCollection
    private List<String> options;

    @Expose(serialize = false)
    @ElementCollection
    private List<String> answer;

    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Question( String title, String text, List<String> options,  List<String> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<String> getAnswer() { return answer; }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions( List<String> options) {
        this.options = options;
    }

    public void setAnswer( List<String> answer) {
        this.answer = answer;
    }

    // Helper classes
    public static JsonObject getJsonWithAnswer(Question question){
        return new Gson().fromJson(new Gson().toJson(question), JsonObject.class);
    }

    public static JsonObject getJasonWithoutAnswer(Question question){
        return new Gson().fromJson(gson.toJson(question), JsonObject.class);
    }

    public static Question JsonToQuest(JsonObject question){
        return gson.fromJson(question, Question.class);
    }

    @Override
    public String  toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", answer=" + answer +
                '}';
    }
}
