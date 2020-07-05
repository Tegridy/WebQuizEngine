package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.util.Arrays;

public class Question {
    @Expose
    private final int id;

    @Expose
    @NotBlank
    private final String title;

    @Expose
    @NotBlank
    private final String text;

    @Expose
    @NotEmpty
    @Size(min = 2)
    private final String[] options;

    @Expose(serialize = false)
    private final int[] answer;

    private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Question(int id, String title, String text, String[] options, int[] answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
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

    public String[] getOptions() {
        return options;
    }

    public int[] getAnswer() { return answer; }


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
                ", options=" + Arrays.toString(options) +
                ", answer=" + answer +
                '}';
    }
}
