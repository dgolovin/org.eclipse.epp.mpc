/*******************************************************************************
 * Copyright (c) 2010, 2018 The Eclipse Foundation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Yatta Solutions - initial API and implementation
 *******************************************************************************/
package org.eclipse.epp.mpc.tests;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class UISuite extends LoggingSuite {

	public UISuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
	}

	public UISuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
		super(builder, classes);
	}

	void doRun(RunNotifier notifier) {
		super.run(notifier);
	}

	@Override
	public void run(final RunNotifier notifier) {
		if (!PlatformUI.isWorkbenchRunning()) {
			notifier.fireTestFailure(new Failure(getDescription(), new IllegalStateException("Workbench not running")));
		}
		if (Display.getCurrent() != null) {
			super.run(notifier);//we are in an UI Thread already
		} else {
			final Display display = PlatformUI.getWorkbench().getDisplay();
			display.syncExec(() -> doRun(notifier));
		}
	}

}
