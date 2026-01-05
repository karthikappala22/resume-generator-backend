package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.dto.ResumeResponse;
import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.service.HtmlToPdfService;
import com.resumeplatform.resumecore.service.ResumeQueryService;
import com.resumeplatform.resumecore.repository.ResumeRepository;
import com.resumeplatform.resumecore.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/resumes")
public class ResumeHtmlPdfController {

	private final ResumeQueryService resumeQueryService;
	private final UserService userService;
	private final TemplateEngine templateEngine;
	private final HtmlToPdfService htmlToPdfService;

	public ResumeHtmlPdfController(ResumeQueryService resumeQueryService, UserService userService,
			TemplateEngine templateEngine, HtmlToPdfService htmlToPdfService) {

		this.resumeQueryService = resumeQueryService;
		this.userService = userService;
		this.templateEngine = templateEngine;
		this.htmlToPdfService = htmlToPdfService;
	}

	
	@GetMapping("/{resumeId}/pdf")
	public void downloadResumePdf(@PathVariable Long resumeId, HttpServletResponse response, HttpSession session) {

		User loggedInUser = userService.getLoggedInUser();

		//System.out.println(loggedInUserId);

		ResumeResponse resume = resumeQueryService.getFullResume(resumeId);

		if (!resume.getUserId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		Context context = new Context();
		context.setVariable("resume", resume);

		String html = templateEngine.process("resume", context);
		byte[] pdf = htmlToPdfService.generatePdf(html);

		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf");

		try {
			response.getOutputStream().write(pdf);
			response.flushBuffer();
		} catch (Exception e) {
			throw new RuntimeException("PDF generation failed", e);
		}
	}

	/*
	 * @GetMapping("/{resumeId}/pdf") public void downloadResumePdf(@PathVariable
	 * Long resumeId, HttpServletResponse response, HttpSession session) {
	 * 
	 * String email = (String) session.getAttribute("EMAIL"); if (email == null) {
	 * throw new RuntimeException("User not logged in"); }
	 * 
	 * ResumeResponse resume = resumeQueryService.getFullResume(resumeId);
	 * 
	 * Context context = new Context(); context.setVariable("resume", resume);
	 * 
	 * String html = templateEngine.process("resume", context); byte[] pdf =
	 * htmlToPdfService.generatePdf(html);
	 * 
	 * response.setContentType(MediaType.APPLICATION_PDF_VALUE);
	 * response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=resume.pdf");
	 * 
	 * try { response.getOutputStream().write(pdf); response.flushBuffer(); } catch
	 * (Exception e) { throw new RuntimeException("PDF generation failed", e); } }
	 */

}
