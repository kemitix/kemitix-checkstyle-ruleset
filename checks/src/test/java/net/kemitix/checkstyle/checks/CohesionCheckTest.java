package net.kemitix.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link CohesionCheck}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class CohesionCheckTest {

    private Checker checker;

    @Mock
    private AuditListener listener;

    @Captor
    private ArgumentCaptor<AuditEvent> auditEvent;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final DefaultConfiguration configuration = new DefaultConfiguration("configuration");
        configuration.addAttribute("charset", "UTF-8");
        final DefaultConfiguration treeConfiguration = new DefaultConfiguration(TreeWalker.class.getName());
        configuration.addChild(treeConfiguration);
        treeConfiguration.addChild(new DefaultConfiguration(CohesionCheck.class.getName()));
        checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread()
                                           .getContextClassLoader());
        checker.configure(configuration);
        checker.addListener(listener);
    }

    @Test
    public void shouldAuditSimpleClassWithNoErrors() throws CheckstyleException {
        //given
        final String className = "SimpleClass.java";
        final List<File> files = fileListForClass(className);
        //when
        checker.process(files);
        //then
        startFileAudit(className);
        then(listener).should(never())
                      .addError(any());
        finishFileAudit(className);
    }

    private void startFileAudit(final String className) {
        then(listener).should()
                      .auditStarted(any());
        then(listener).should()
                      .fileStarted(auditEvent.capture());
        auditEventFileIsFor(className);
    }

    private void auditEventFileIsFor(final String className) {
        assertThat(auditEvent.getValue()
                             .getFileName()).endsWith(className);
        auditEvent.getAllValues()
                  .clear();
    }

    private void finishFileAudit(final String className) {
        then(listener).should()
                      .fileFinished(auditEvent.capture());
        auditEventFileIsFor(className);
        then(listener).should()
                      .auditFinished(any());
    }

    private List<File> fileListForClass(final String className) {
        final File classFile = new File(getClass().getResource(className)
                                                  .getFile());
        return Collections.singletonList(classFile);
    }

    @Test
    public void shouldAuditPartitionedClassWithError() throws CheckstyleException {
        //given
        final String className = "SimplePartitionedClass.java";
        final List<File> files = fileListForClass(className);
        //when
        checker.process(files);
        //then
        startFileAudit(className);

        then(listener).should()
                      .addError(auditEvent.capture());
        final AuditEvent event = auditEvent.getValue();
        assertThat(event).returns(CohesionCheck.class.getName(), AuditEvent::getSourceName)
                         .returns("Class is partitioned by fields: [left], [right]", AuditEvent::getMessage);

        finishFileAudit(className);
    }
}
