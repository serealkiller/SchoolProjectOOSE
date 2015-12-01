package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionnaireController {

	@Autowired
    private LocalQuestionService localQuestionService;

    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;
    
    /**
     * If the file path relative to the base was "/session", return the relevant web page.
     * 
     * Does this page even exist?
     */
	@RequestMapping(value="/session", method=RequestMethod.GET)
    public String session(Model model, HttpSession session) {
		session.setAttribute("questionnaire", 1);
		model.addAttribute("name", "World");
		return "greeting";
	}
	
	/**
	 * If the file path relative to the base was "/questionnaire", return the relevant web page.
	 * 
	 * If no questionnaire exists, the "questionnaire" web page is returned.
	 * 
	 * If a questionnaire already exists, redirects to the "showQuestion" web page.
	 */
    @RequestMapping(value="/questionnaire", method=RequestMethod.GET)
    public String questionnaire(Model model, HttpSession session) {
    	if(session.getAttribute("questionnaire") == null) {
    		return "questionnaire";
    	} else {
    		return "redirect:/showQuestion";
    	}
    }
    
    /**
     * If the file path relative to the base was "/showQuestion", return the relevant web page.
     * 
     * If no questionnaire exists (i.e. it hasn't been started yet), a new questionnaire is created and a new test is started.
     * Returns the "questionnaire" web page.
     * 
     * If a questionnaire already exists, it is loaded from the session.
     * Submits the answer, and returns the next relevant web page.
     */
    @Transactional
    @RequestMapping(value="/showQuestion", method=RequestMethod.POST)
    public String showQuestionPOST(Model model, HttpSession session, HttpServletRequest httpServletRequest) {
    	Questionnaire questionnaire = null;
    	if(session.getAttribute("questionnaire") == null) {
			questionnaire = new Questionnaire();
			questionnaire.startNewTest(model, session, localQuestionService);
		} else {
			if(session.getAttribute("questionnaire") instanceof Questionnaire) {
				questionnaire = (Questionnaire) session.getAttribute("questionnaire");
			}
			return questionnaire.submitAnswer(httpServletRequest, localQuestionService, localPersonalityTypeService, model, session);
		}
		return "questionnaire";
    }
    
    /**
	 * If the file path relative to the base was "/showQuestion", return the relevant web page.
	 * 
	 * If no questionnaire exists (i.e. it hasn't been started yet), the questionnaire web page isl loaded.
	 * 
	 * If a questionnaire already exists, it is loaded from the session.
	 * Retrieves the next question, and displays it on the web page.
     */
	@RequestMapping(value="/showQuestion", method=RequestMethod.GET)
    public String showQuestionGET(Model model, HttpSession session) {
    	Questionnaire questionnaire = null;
		if(session.getAttribute("questionnaire") == null) {
			return "questionnaire";
		} else {
			if(session.getAttribute("questionnaire") instanceof Questionnaire) {
				questionnaire = (Questionnaire) session.getAttribute("questionnaire");
			}
			questionnaire.getCurrentQuestion(model);
		}
		return "questionnaire";
	}
}