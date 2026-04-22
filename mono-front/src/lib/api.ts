export const API_BASE = 'http://localhost:8080';

type UserRequest = {
	userId: string;
	userName: string;
};

export class ApiError extends Error {
	status: number;
	code?: string;

	constructor(status: number, message: string, code?: string) {
		super(message);
		this.status = status;
		this.code = code;
	}
}

async function handleResponse(res: Response) {
	if (!res.ok) {
		let message = 'エラーが発生しました';
		let code: string | undefined;

		try {
			const err = await res.json();
			message = err.message ?? message;
			code = err.code;
		} catch {
			// JSONパース失敗時はデフォルト
		}

		throw new ApiError(res.status, message, code);
	}

	if (res.status === 204) {
		return null;
	}

	return res.json();
}

export async function getUsers(fetch: typeof globalThis.fetch, params: URLSearchParams) {
	const res = await fetch(`${API_BASE}/users?${params}`);
	return handleResponse(res);
}

export async function getUser(fetch: typeof globalThis.fetch, id: string) {
	const res = await fetch(`${API_BASE}/users/${id}`);
	return handleResponse(res);
}

export async function createUser(fetch: typeof globalThis.fetch, body: UserRequest) {
	const res = await fetch(`${API_BASE}/users`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(body)
	});
	return handleResponse(res);
}

export async function updateUser(fetch: typeof globalThis.fetch, id: string, body: UserRequest) {
	const res = await fetch(`${API_BASE}/users/${id}`, {
		method: 'PUT',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(body)
	});
	return handleResponse(res);
}

export async function deleteUser(fetch: typeof globalThis.fetch, id: string) {
	const res = await fetch(`${API_BASE}/users/${id}`, {
		method: 'DELETE'
	});
	return handleResponse(res);
}