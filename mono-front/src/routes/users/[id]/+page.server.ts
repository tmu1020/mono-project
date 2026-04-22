import type { PageServerLoad, Actions } from './$types';
import { redirect, fail, error } from '@sveltejs/kit';
import { getUser, createUser, updateUser, deleteUser, ApiError } from '$lib/api';

export const load: PageServerLoad = async ({ fetch, params }) => {

	if (params.id === 'new') {
		return { user: null };
	}

	try {
		const user = await getUser(fetch, params.id);
		return { user };
	} catch (e: unknown) {

		if (e instanceof ApiError) {
			throw error(e.status, e.message);
		}

		throw error(500, 'エラー発生');
	}
};

export const actions: Actions = {

	save: async ({ request, fetch, params }) => {

		const form = await request.formData();

		const userId = form.get('userId')?.toString();
		const userName = form.get('userName')?.toString();

		if (!userId || !userName) {
			return fail(400, { message: '入力必須' });
		}

		try {

			if (params.id === 'new') {
				await createUser(fetch, { userId, userName });
			} else {
				await updateUser(fetch, params.id, { userId, userName });
			}

		} catch (e: unknown) {

			if (e instanceof ApiError) {
				return fail(e.status, {
					message: e.message,
					code: e.code
				});
			}

			return fail(500, {
				message: 'エラー発生'
			});
		}

		throw redirect(303, '/users');
	},

	delete: async ({ fetch, params }) => {

		try {
			await deleteUser(fetch, params.id);
		} catch (e: unknown) {

			if (e instanceof ApiError) {
				return fail(e.status, {
					message: e.message,
					code: e.code
				});
			}

			return fail(500, {
				message: 'エラー発生'
			});
		}

		throw redirect(303, '/users');
	}
};