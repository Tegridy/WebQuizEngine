package engine.quiz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
//@Table(name = "question")
public class Question {
    @Expose
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
    @Column
    private String options;

    @Expose(serialize = false)
    @Column
    private String answer;

    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Question(int id, String title, String text, String options, String answer) {
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

    public String getOptions() {
        return options;
    }

    public String getAnswer() { return answer; }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setAnswer(String answer) {
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
