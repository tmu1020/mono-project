import type { PageServerLoad, Actions } from './$types';
import { redirect, fail } from '@sveltejs/kit';
import { getUser, createUser, updateUser, deleteUser } from '$lib/api';

export const load: PageServerLoad = async ({ fetch, params }) => {

	if (params.id === 'new') {
		return { user: null };
	}

	const user = await getUser(fetch, params.id);

	return { user };
};

export const actions: Actions = {

	save: async ({ request, fetch, params }) => {

		const form = await request.formData();

		const userId = form.get('userId')?.toString();
		const userName = form.get('userName')?.toString();

		if (!userId || !userName) {
			return fail(401, { message: '入力必須' });
		}

		try {

			if (params.id === 'new') {
				await createUser(fetch, { userId, userName });
			} else {
				await updateUser(fetch, params.id, { userId, userName });
			}

		} catch (e: unknown) {
			return fail(400, {
				message: e instanceof Error ? e.message : 'エラー発生'
			});
		}

		throw redirect(303, '/users');
	},

	delete: async ({ fetch, params }) => {

		try {
			await deleteUser(fetch, params.id);
		} catch (e: unknown) {
			return fail(400, {
				message: e instanceof Error ? e.message : 'エラー発生'
			});
		}

		throw redirect(303, '/users');
	}
};