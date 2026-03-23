import type { PageServerLoad } from './$types';
import { getUsers } from '$lib/api';

export const load: PageServerLoad = async ({ fetch, url }) => {

	const page = Number(url.searchParams.get('page') ?? '1');
	const sort = url.searchParams.get('sort') ?? 'id';
	const order = url.searchParams.get('order') ?? 'asc';

	const userId = url.searchParams.get('userId') ?? '';
	const userName = url.searchParams.get('userName') ?? '';

	const size = 5;

	const params = new URLSearchParams({
		page: String(page - 1),
		size: String(size),
		sort: `${sort},${order}`
	});

	if (userId) params.append('userId', userId);
	if (userName) params.append('userName', userName);

	const result = await getUsers(fetch, params);

	return {
		users: result.data,
		total: result.total,
		page,
		sort,
		order,
		userId,
		userName
	};
};