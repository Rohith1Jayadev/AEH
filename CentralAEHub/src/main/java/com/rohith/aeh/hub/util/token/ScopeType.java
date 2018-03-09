package com.rohith.aeh.hub.util.token;

public enum ScopeType {

	READ("READ"), WRITE("WRITE"), ALL("ALL");
	private String scope;

	private ScopeType(String scopeType) {
		this.scope = scopeType;
	}

	public String scopeType() {
		return this.scope;
	}

	public static ScopeType isEqual(String scope) {

		if (ScopeType.READ.scopeType().equals(scope)) {

			return ScopeType.READ;
		} else if (ScopeType.WRITE.scopeType().equals(scope)) {
			return ScopeType.WRITE;
		}

		return ScopeType.ALL;
	}

	public static ScopeType isMethodEqual(String method) {

		if ("GET".equalsIgnoreCase(method)) {

			return ScopeType.READ;
		} else if ("POST".equalsIgnoreCase(method)) {

			return ScopeType.WRITE;
		} else if ("PUT".equalsIgnoreCase(method)) {

			return ScopeType.WRITE;
		} else {

			return ScopeType.WRITE;
		}
	}
}
