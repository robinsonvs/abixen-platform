/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.webcontent.controller;

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.service.webcontent.dto.TemplateDto;
import com.abixen.platform.service.webcontent.facade.TemplateFacade;
import com.abixen.platform.service.webcontent.form.TemplateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/templates")
public class TemplateController {

    private final TemplateFacade templateFacade;

    @Autowired
    public TemplateController(TemplateFacade templateFacade) {
        this.templateFacade = templateFacade;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createTemplate(@RequestBody @Valid TemplateForm templateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(templateForm, formErrors);
        }

        templateFacade.createTemplate(templateForm);

        return new FormValidationResultDto(templateForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateTemplate(@PathVariable("id") Long id, @RequestBody @Valid TemplateForm templateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(templateForm, formErrors);
        }

        templateFacade.updateTemplate(templateForm);

        return new FormValidationResultDto(templateForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTemplate(@PathVariable Long id) {
        templateFacade.deleteTemplate(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TemplateDto getTemplate(@PathVariable Long id) {
        return templateFacade.findTemplate(id);
    }

    @RequestMapping(value = "/{id}/variables", method = RequestMethod.GET)
    public List<String> getTemplateVariables(@PathVariable Long id) {
        List<String> variables = templateFacade.getTemplateVariables(id);
        return variables;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<TemplateDto> getTemplates(@PageableDefault(size = 1) Pageable pageable) {
        return templateFacade.findAllTemplates(pageable);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<TemplateDto> getTemplates() {
        return templateFacade.findAllTemplates();
    }
}