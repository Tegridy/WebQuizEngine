package engine.controller;


import com.google.gson.*;
import engine.quiz.Question;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@SuppressWarnings("deprecation")
public class QuizController {

    private final HttpHeaders headers = new HttpHeaders();
    private static final ArrayList<JsonObject> listOfQuestions = new ArrayList<>();
    // private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static int id = 0;

    private final String WRONG_ANSWER = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
    private final String CORRECT_ANSWER = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";

    @EventListener(ApplicationReadyEvent.class)
    public void setHeaders() {
        //do pre work
        headers.add("Content-Type", "application/json");
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public ResponseEntity<JsonObject> createQuiz(@Valid @RequestBody Question question){

        JsonObject questToList = Question.getJsonWithAnswer(question);
        questToList.addProperty("id", id);

        listOfQuestions.add(questToList);

        JsonObject responseQuest = Question.getJasonWithoutAnswer(question);
        responseQuest.addProperty("id", id);

        id++;
        return new ResponseEntity<>(responseQuest, headers, HttpStatus.OK);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<JsonObject> getQuizById(@PathVariable int id){

        if (id > listOfQuestions.size()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Question quest = Question.JsonToQuest(listOfQuestions.get(id));

        JsonObject responseQuest = Question.getJasonWithoutAnswer(quest);

        return new ResponseEntity<>(responseQuest, headers, HttpStatus.OK);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<JsonArray> getQuizById(){
        JsonArray quizzesList = new JsonArray();

        for (JsonObject q : listOfQuestions) {
            Question quest = Question.JsonToQuest(q);
            quizzesList.add(Question.getJasonWithoutAnswer(quest));
        }

        return new ResponseEntity<>(quizzesList, headers, HttpStatus.OK);
    }

    @PostMapping("quizzes/{id}/solve")
    public ResponseEntity<JsonObject> solveQuiz(@PathVariable int id, @RequestBody JsonObject answer){

        if (id > listOfQuestions.size()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String json;
        JsonArray answer1 = answer.get("answer").getAsJsonArray();
        JsonArray question = listOfQuestions.get(id).has("answer")
                ? listOfQuestions.get(id).get("answer").getAsJsonArray() : new JsonArray();


        if (question.equals(answer1)){
            json = CORRECT_ANSWER;
        } else if (!listOfQuestions.get(id).has("answer") && answer1.size() == 0) {
            json = WRONG_ANSWER;
        } else {
            json = WRONG_ANSWER;
        }
        JsonObject responseAnswer = new JsonParser().parse(json).getAsJsonObject();

        return new ResponseEntity<>(responseAnswer, headers, HttpStatus.OK);
    }
}
