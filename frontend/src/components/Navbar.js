import { Link, useLocation } from 'react-router-dom';
import { isAuthenticated, logout } from './Utils';
import { ReactComponent as Diamond } from './surveyCreation/icons/diamond.svg';
const Navbar = () => {
	const handleLogout = () => {
		logout();
	};
	let location = useLocation();
	return (
		<div className='h-16 bg-gradient-to-r from-m-pink to-m-orange absolute w-full z-50 shadow-md flex justify-between px-8 text-white text-xl capitalize rounded-b-md'>
			<Link to='/' className='items-center flex'>
				<div>
					<Diamond/>
				</div>
				<div className='pl-4 text-3xl font-bold'>Next Surveys</div>
			</Link>
			<div className='items-center flex gap-6 text-xl'>
				<div>
					{location.pathname !== '/create' && isAuthenticated() && (
						<Link
							to='/create'
							className='border-b border-transparent hover:border-white'
						>
							Create
						</Link>
					)}
				</div>
				<div>
					{location.pathname !== '/user' && isAuthenticated() && (
						<Link
							to='/user'
							className='border-b border-transparent hover:border-white'
						>
							My Surveys
						</Link>
					)}
				</div>
				{isAuthenticated() && (
					<Link
						to='/'
						className='border-b border-transparent hover:border-white cursor-pointer'
						onClick={handleLogout}
					>
						Logout
					</Link>
				)}
			</div>
		</div>
	);
};

export default Navbar;
