
const UUIDv4 = () => {
	// eslint-disable-next-line
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
		var r = (Math.random() * 16) | 0,
			v = c === 'x' ? r : (r & 0x3) | 0x8;
		return v.toString(16);
	});
};

export default UUIDv4;

export const isAuthenticated = () => localStorage.token != null;

export const logout = () => {
	localStorage.clear();
};

export function AuthHeader() {
	let headers = { };

	if (localStorage.token) {
		headers.Authorization = `Bearer ${localStorage.token}`;
	}

	return headers;
}
