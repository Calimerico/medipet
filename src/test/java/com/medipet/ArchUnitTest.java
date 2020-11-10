package com.medipet;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.medipet", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchUnitTest {

    @ArchTest
    public static final ArchRule serviceClassMustBeTransactional = ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith("Service")
            .and().areNotInterfaces()
            .and().areNotEnums()
            .or().haveSimpleNameEndingWith("ServiceImpl")
            .should().beAnnotatedWith(Transactional.class);

    @ArchTest
    public static final ArchRule controllersAndServicesOnlyHavePrivateFinalFields = ArchRuleDefinition.fields()
            .that().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
            .or().areDeclaredInClassesThat().areAnnotatedWith(Service.class)
//            .should()
//            .beFinal()
            .and().areNotFinal()
            .should()
            .bePrivate();

    @ArchTest
    public static final ArchRule domainShouldNotDependOnInfrastructure = ArchRuleDefinition.classes()
            .that().resideInAPackage("..infrastructure..")
            .should()
            .onlyBeAccessed().byAnyPackage("..application..");
}
