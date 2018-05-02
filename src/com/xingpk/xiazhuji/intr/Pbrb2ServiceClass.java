package com.xingpk.xiazhuji.intr;

import java.util.List;

public interface Pbrb2ServiceClass {
    String makeAnnotationString();
    String makeVarImportString(List<Pbrb2ServiceClass> list);
    String makeBegainString();
    String makeResponseString();
    String makeCallString(Pbrb2ServiceClass serviceClass, String indent);
    String makeFailString(Pbrb2ServiceClass serviceClass);
    String makeEndString();
    IO getInput();
    IO getOutput();
}
