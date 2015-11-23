package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionnaireController {

	@Autowired
    private LocalQuestionService localQuestionService;
	
	
	@RequestMapping(value="/session", method=RequestMethod.GET)
    public String session(Model model, HttpSession session) {
		
		session.setAttribute("questionnaire", 1);
		model.addAttribute("name", "World");
		return "greeting";
	}
	
    @RequestMapping(value="/questionnaire", method=RequestMethod.GET)
    public String questionnaire(Model model, HttpSession session) {
    	
    	if(session.getAttribute("questionnaire") == null) {
    		return "questionnaire";
    	} else {
    		return "redirect:/showQuestion";
    	}
    }
    
    @RequestMapping(value="/showQuestion", method=RequestMethod.POST)
    public String showQuestionPOST(Model model, HttpSession session, HttpServletRequest httpServletRequest) {
    	
    	Questionnaire questionnaire = null;
    	if(session.getAttribute("questionnaire") == null) {
			questionnaire = new Questionnaire();
			session.setAttribute("questionnaire", questionnaire);		
			
			Question firstQuestion = localQuestionService.getFirstQuestion(questionnaire);
			model.addAttribute("currentQuestion", firstQuestion);
			
		} else {
			
			if(session.getAttribute("questionnaire") instanceof Questionnaire) {
				questionnaire = (Questionnaire) session.getAttribute("questionnaire");
			}
			
			List<Question> answeredQuestions = questionnaire.getAnsweredQuestions();
			Question previousQuestion = answeredQuestions.get(answeredQuestions.size()-1);
			
			localQuestionService.setQuestionAnswer(httpServletRequest, previousQuestion);		
			Question nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
			
			if(nextQuestion != null) {
				questionnaire.addQuestion(nextQuestion);
				model.addAttribute("currentQuestion", nextQuestion);
			}
			else {
				model.addAttribute("result", questionnaire.getAnsweredQuestions());
				return "result";
			}				
		}
		return "questionnaire";
    }
	
    @RequestMapping(value="/showQuestion", method=RequestMethod.GET)
    public String showQuestionGET(Model model, HttpSession session) {
		
    	Questionnaire questionnaire = null;

		if(session.getAttribute("questionnaire") == null) {
			return "questionnaire";

		} else {
			if(session.getAttribute("questionnaire") instanceof Questionnaire) {
				questionnaire = (Questionnaire) session.getAttribute("questionnaire");
			}
			
			List<Question> answeredQuestions = questionnaire.getAnsweredQuestions();
			Question currentQuestion = answeredQuestions.get(answeredQuestions.size()-1);
			model.addAttribute("currentQuestion", currentQuestion);
		}
		
		return "questionnaire";
	}

}