package swtbot.example.tests;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class SampleViewTest {

	private static SWTWorkbenchBot bot;

	@BeforeClass
	public static void initBot() throws InterruptedException {
		bot = new SWTWorkbenchBot();
		closeWelcomePage();
		openJavaPerspective();
	}

	private static void closeWelcomePage() {
		for (SWTBotView view : bot.views()) {
			if (view.getTitle().equals("Welcome")) {
				view.close();
			}
		}
	}

	@AfterClass
	public static void afterClass() {
		bot.resetWorkbench();
	}

	@Test
	public void testSampleViewInJavaPerspective() {
		bot.viewByTitle("Sample View").show();
	}

	private static void openJavaPerspective() throws InterruptedException {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					PlatformUI.getWorkbench().showPerspective(JavaUI.ID_PERSPECTIVE,       
					         PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				} catch (WorkbenchException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
