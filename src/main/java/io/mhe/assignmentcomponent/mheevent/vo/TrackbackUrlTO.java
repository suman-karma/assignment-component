package io.mhe.assignmentcomponent.mheevent.vo;

public class TrackbackUrlTO {

private String className;
private String methodName;
private String scenario;

public TrackbackUrlTO(String scenario, String methodName, String className) {
		this.className = className;
		this.methodName = methodName;
		this.scenario = scenario;
}
public String getClassName() {
		return className;
}
public void setClassName(String className) {
		this.className = className;
}
public String getMethodName() {
		return methodName;
}
public void setMethodName(String methodName) {
		this.methodName = methodName;
}
public String getScenario() {
		return scenario;
}
public void setScenario(String scenario) {
		this.scenario = scenario;
}

@Override
public String toString() {
		return "TrackbackUrlTO [className=" + className + ", methodName=" + methodName + ", scenario=" + scenario + "]";
}

}
