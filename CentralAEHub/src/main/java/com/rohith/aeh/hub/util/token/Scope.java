package com.rohith.aeh.hub.util.token;

public class Scope {

	private String scopeValue;

	private ScopeType scopeType;

	public String getScopeValue() {
		return scopeValue;
	}

	public void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}

	public ScopeType getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeType scopeType) {
		this.scopeType = scopeType;
	}

	@Override
	public boolean equals(Object scope) {
		Scope other = (Scope) scope;
		if (null != this.scopeValue && null != other.scopeValue && null != this.scopeType && null != other.scopeType) {
			if (checkType(this.scopeType, other.scopeType) && checkEqual(this.scopeValue, other.scopeValue)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean checkType(ScopeType thisScopeType, ScopeType otherScopeType) {

		if (this.scopeType == ScopeType.ALL) {

			return true;
		}
		return (thisScopeType == otherScopeType);
	}

	private boolean checkEqual(String thisValue, String otherValue) {

		String[] thisValues = thisValue.split("/");

		String[] otherValues = otherValue.split("/");

		int minLength = Math.min(thisValues.length, otherValues.length);

		if (minLength == 0) {

			return false;
		}

		for (int i = 0; i < minLength; i++) {
			if (thisValues[i].equals("*")) {
				return true;
			}
			if (thisValues[i].equals(otherValues[i])||otherValues[i].equals(" ")) {
				
				if(i==(minLength-1)){
					
					return true;
				}
				continue;
			} else {
				return false;
			}
		}
		return false;
	}

	public String toScopeString() {

		return this.getScopeType().scopeType() + "#" + this.getScopeValue();
	}

	public static Scope fromScopeString(String scopeString) {

		if (null == scopeString || "null".equals(scopeString)) {

			return null;
		}
		String[] split = scopeString.split("#");
		if (split.length != 2) {
			return null;
		}
		Scope scope = parse(split);
		return scope;
	}

	public static Scope fromRequestedScope(String requestedScope, String requestedType) {
		if (null == requestedScope || "null".equals(requestedScope) || null == requestedType
				|| "null".equals(requestedType)) {
			return null;
		} else {
			Scope scope = new Scope();
			scope.setScopeValue(requestedScope);
			scope.setScopeType(ScopeType.isMethodEqual(requestedType));
			return scope;
		}
	}

	private static Scope parse(String[] split) {
		Scope scope = new Scope();

		scope.setScopeType(ScopeType.isEqual(split[0]));
		scope.setScopeValue(split[1]);
		return scope;
	}

}
