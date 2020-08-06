package engine.controller;


import com.google.gson.*;
import engine.quiz.Question;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@SuppressWarnings("deprecation")
public class QuizController {

    private final HttpHeaders headers = new HttpHeaders();
    // private static final ArrayList<JsonObject> listOfQuestions = new ArrayList<>();
    // private final static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    // private static int id = 0;
    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    private final String WRONG_ANSWER = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
    private final String CORRECT_ANSWER = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";

    @EventListener(ApplicationReadyEvent.class)
    public void setHeaders() {
        //do pre work
        headers.add("Content-Type", "application/json");
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public ResponseEntity<JsonObject> createQuiz(@Valid @RequestBody Question question){

        quizService.saveQuestion(question);

//        JsonObject questToList = Question.getJsonWithAnswer(question);
//        questToList.addProperty("id", id);
//
//        listOfQuestions.add(questToList);

        JsonObject responseQuest = Question.getJasonWithoutAnswer(question);
//        responseQuest.addProperty("id", id);
//
//        id++;
        return new ResponseEntity<>(responseQuest, headers, HttpStatus.OK);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<JsonObject> getQuizById(@PathVariable int id){

        Question result = quizService.findQuestionById(id);

        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JsonObject responseQuest = Question.getJasonWithoutAnswer(result);

        return new ResponseEntity<>(responseQuest, headers, HttpStatus.OK);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<JsonArray> getQuizById(){
        JsonArray quizzesList = new JsonArray();

        List<Question> listOfQuestions = quizService.getAllQuestions();

        for (Question q : listOfQuestions) {
            //Question quest = Question.JsonToQuest(q);
            quizzesList.add(Question.getJasonWithoutAnswer(q));
        }

        return new ResponseEntity<>(quizzesList, headers, HttpStatus.OK);
    }

    @PostMapping("quizzes/{id}/solve")
    public ResponseEntity<JsonObject> solveQuiz(@PathVariable int id, @RequestBody JsonObject answer){

        List<Question> listOfQuestions = quizService.getAllQuestions();

        System.out.println(listOfQuestions.size());
        System.out.println(id);

        if (id >= listOfQuestions.size() || id < 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String json;
//        JsonArray answer1 = answer.get("answer").getAsJsonArray();
//        JsonArray question = Question.getJsonWithAnswer(listOfQuestions.get(id)).has("answer")
//                ? Question.getJsonWithAnswer(listOfQuestions.get(id)).get("answer").getAsJsonArray() : new JsonArray();

        String answer1 = answer.get("answer").getAsJsonArray().toString();
        String question = Question.getJsonWithAnswer(listOfQuestions.get(id)).has("answer")
                ? Question.getJsonWithAnswer(listOfQuestions.get(id)).get("answer").toString().replaceAll("\"", "") : "";

        System.out.println(answer1);
        System.out.println(question);


        if (question.equals(answer1)){
            json = CORRECT_ANSWER;
        } else if (!Question.getJsonWithAnswer(listOfQuestions.get(id)).has("answer") && answer1.length() == 0) {
            json = WRONG_ANSWER;
        } else {
            json = WRONG_ANSWER;
        }
        JsonObject responseAnswer = new JsonParser().parse(json).getAsJsonObject();

        return new ResponseEntity<>(responseAnswer, headers, HttpStatus.OK);
    }

    @GetMapping("/test")
    private ResponseEntity<List<Question>> temp(){
        List<Question> res = quizService.getAllQuestions();
        System.out.println(res.toString());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
