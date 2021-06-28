import { Redirect } from 'react-router';
import { useState } from 'react';
import Login from './login/Login';
import { ReactComponent as Splash } from './surveyCreation/icons/splash.svg';
import { isAuthenticated } from './Utils';
const Main = () => {
	const [successRegister, setSuccessRegister] = useState(false);
	if (isAuthenticated()) return <Redirect to='/user' />;
	return (
		<div className='bg-purple-100 w-full'>
			<div className='p-64 grid grid-cols-2 h-screen relative'>
				{successRegister && (
					<div
						className={`absolute top-24 border shadow-md rounded-full bg-white flex justify-self-center text-xl p-4`}
					>
						
						<p>
							One more step until you can access your account. Go to your e-mail and
							complete the registration.
						</p>
					</div>
				)}
				<div className='font-montserrat relative w-max p-6'>
					<div className='z-10 relative text-white'>
						<div className='font-bold text-7xl pb-24 pt-12'>Next Surveys</div>
						<p className='text-4xl'>
							Make online surveys, <br />
							Get the <span className='font-bold'>Next</span> opinions
						</p>
					</div>
					<div className='z-0'>
						<Splash className='absolute -top-12 -left-12' />
					</div>
				</div>
				<div>
					<Login setSuccessRegister={setSuccessRegister} />
				</div>
			</div>
		</div>
	);
};

export default Main;
