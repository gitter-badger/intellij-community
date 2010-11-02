/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi.util.proximity;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.util.NotNullLazyKey;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.ProximityLocation;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.util.ArrayUtil;
import com.intellij.util.NotNullFunction;
import org.jetbrains.annotations.NotNull;

/**
 * @author peter
*/
public class OpenedInEditorWeigher extends ProximityWeigher {
  private static final NotNullLazyKey<VirtualFile[], ProximityLocation> OPENED_EDITORS = NotNullLazyKey.create("openedEditors", new NotNullFunction<ProximityLocation, VirtualFile[]>() {
    @NotNull
    @Override
    public VirtualFile[] fun(ProximityLocation location) {
      return FileEditorManager.getInstance(location.getProject()).getOpenFiles();
    }
  });

  public Comparable weigh(@NotNull final PsiElement element, final ProximityLocation location) {
    final VirtualFile virtualFile = PsiUtilBase.getVirtualFile(element);
    return virtualFile != null && ArrayUtil.find(OPENED_EDITORS.getValue(location), virtualFile) != -1;
  }
}
