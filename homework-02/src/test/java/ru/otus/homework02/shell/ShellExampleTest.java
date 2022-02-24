package ru.otus.homework02.shell;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework02.service.StudentTestStartService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест команд shell ")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class ShellExampleTest {

    @MockBean
    private StudentTestStartService service;
    @MockBean
    private MessageSource messageSource;

    @Autowired
    private Shell shell;

    private static final String COMMAND_START = "start";
    private static final String COMMAND_SHORT_START = "s";


    @DisplayName("должен запустить тестирование по полной команде")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void runTestingFullCommandTest() {
        shell.evaluate(() -> COMMAND_START);
        verify(service, times(1)).start(messageSource);
    }

    @DisplayName("должен запустить тестирование по полной команде")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void runTestingShortCommandTest() {
        shell.evaluate(() -> COMMAND_SHORT_START);
        verify(service, times(1)).start(messageSource);
    }
}