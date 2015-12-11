package swtbot.example.tests;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.IWorkbench;
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

	@Test
	public void testViewTree() {
		SWTBotView view = bot.viewByTitle("Sample View");
		view.bot().tree().getTreeItem("Root").expand();
		view.bot().tree().getTreeItem("Root").getNode("Parent 1").expand();
		view.bot().tree().getTreeItem("Root").getNode("Parent 1").getNode("Leaf 1").select();
	}

	@Test
	public void testViewTreeDoubleClick() {
		SWTBotView view = bot.viewByTitle("Sample View");
		view.bot().tree().getTreeItem("Root").doubleClick();
		assertDialog("Double-click detected on Root");
	}

	@Test
	public void testViewToolbar() {
		bot.toolbarButtonWithTooltip("Action 1 tooltip").click();
		assertDialog("Action 1 executed");
		bot.toolbarButtonWithTooltip("Action 2 tooltip").click();
		assertDialog("Action 2 executed");
	}

	@Test
	public void testViewTreeContextMenu() {
		SWTBotView view = bot.viewByTitle("Sample View");
		view.bot().tree().getTreeItem("Root").contextMenu("Action 1").click();
		assertDialog("Action 1 executed");
	}

	private void assertDialog(String labelInDialog) {
		SWTBotShell dialog = bot.shell("Sample View");
		dialog.activate();
		bot.label(labelInDialog);
		bot.button("OK").click();
		bot.waitUntil(shellCloses(dialog));
	}

	private static void openJavaPerspective() throws InterruptedException {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					IWorkbench workbench = PlatformUI.getWorkbench();
					workbench.showPerspective("org.eclipse.jdt.ui.JavaPerspective",
							workbench.getActiveWorkbenchWindow());
				} catch (WorkbenchException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
