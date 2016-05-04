package mirrg.todo;

import mirrg.applet.nitrogen.AppletNitrogen;
import mirrg.applet.nitrogen.ModuleComponentEvent;
import mirrg.applet.nitrogen.modules.input.ModuleInputEvent;
import mirrg.applet.nitrogen.modules.input.ModuleInputStatus;
import mirrg.applet.nitrogen.modules.rendering.ModuleTripleBuffer;
import mirrg.applet.nitrogen.modules.threading.ModuleFPSAdjuster;
import mirrg.applet.nitrogen.modules.threading.ModuleGameThread;

public class ModuleStandard
{

	public ModuleGameThread moduleGameThread;
	public ModuleFPSAdjuster moduleFPSAdjuster;

	public ModuleComponentEvent moduleComponentEvent;

	public ModuleTripleBuffer moduleTripleBuffer;

	public ModuleInputEvent moduleInputEvent;
	public ModuleInputStatus moduleInputStatus;

	public ModuleStandard(AppletNitrogen applet)
	{
		initModuleThreading(applet);
		initModuleComponentEvent(applet);
		initModuleRendering(applet);
		initModuleInput(applet);
	}

	public void initModuleThreading(AppletNitrogen applet)
	{
		moduleGameThread = new ModuleGameThread(applet);
		moduleFPSAdjuster = new ModuleFPSAdjuster(applet, moduleGameThread);
	}

	public void initModuleComponentEvent(AppletNitrogen applet)
	{
		moduleComponentEvent = new ModuleComponentEvent(applet);
	}

	public void initModuleRendering(AppletNitrogen applet)
	{
		moduleTripleBuffer = new ModuleTripleBuffer(applet, moduleComponentEvent, moduleGameThread);
	}

	public void initModuleInput(AppletNitrogen applet)
	{
		moduleInputEvent = new ModuleInputEvent(applet);
		moduleInputStatus = new ModuleInputStatus(applet, moduleInputEvent);
	}

}
