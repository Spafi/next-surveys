import React from 'react';
import './login.css';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { loginUrl, registerUrl } from '../../BASE_URL';
import { useHistory } from 'react-router';

const Login = ({ setSuccessRegister }) => {
	const history = useHistory();

	const [firstName, setFirstName] = useState('');
	const [lastName, setLastName] = useState('');
	const [email, setEmail] = useState(document.getElementById('email')?.value);
	const [password, setPassword] = useState(
		document.getElementById('password')?.value
	);
	const [retype, setRetype] = useState('');

	const onChangeEmail = (e) => {
		const email = e.target.value;
		setEmail(email);
	};

	const onChangePassword = (e) => {
		const password = e.target.value;
		setPassword(password);
	};

	const onChangeFirstName = (e) => {
		const firstName = e.target.value;
		setFirstName(firstName);
	};

	const onChangeLastName = (e) => {
		const lastName = e.target.value;
		setLastName(lastName);
	};

	const onChangeRetype = (e) => {
		const retype = e.target.value;
		setRetype(retype);
	};

	useEffect(() => {
		const signUpButton = document.getElementById('signUp');
		const signInButton = document.getElementById('signIn');
		const container = document.getElementById('container');

		signUpButton.addEventListener('click', () => {
			container.classList.add('right-panel-active');
		});

		signInButton.addEventListener('click', () => {
			container.classList.remove('right-panel-active');
		});
	}, []);

	const handleRegister = (e) => {
		const role = 'USER';
		e.preventDefault();
		if (checkPassword()) {
			axios
				.post(
					registerUrl,
					{
						firstName,
						lastName,
						email,
						password,
						role,
					},
					{
						headers: {
							headers: {
								'Access-Control-Allow-Origin': '*',
							},
						},
					}
				)
				.then((response) => {
					if (response.status === 200) {
						showSuccessfulRegistration();
						setSuccessRegister(true)
					}
				})
				.catch((error) => {
					// TODO: implement error handling
					console.log(error.response);
					if (error.response.data.message === 'Email already taken') emailTaken();
				});

			document.getElementById('retype').placeholder = 'Retype Password';
			e.target.reset();
		} else {
			passwordsNotMatching();
		}
	};

	const showSuccessfulRegistration = () => {
		document.getElementById('container').classList.add('successful-registration');
		setTimeout(() => {
			document
				.getElementById('container')
				.classList.remove('successful-registration');
		}, 2000);
	};

	const handleLogin = (e) => {
		e.preventDefault();

		axios
			.post(
				loginUrl,
				{ email, password },
				{
					headers: {
						headers: {
							'Access-Control-Allow-Origin': '*',
						},
					},
				}
			)
			.then((response) => {
				e.target.reset();
				localStorage.setItem('token', response.data.jwt);
				localStorage.setItem('role', response.data.role);
				localStorage.setItem('userId', response.data.userId);
				localStorage.setItem('firstName', response.data.firstName);
				history.push('/user');
			})
			.catch((error) => {
				// TODO: implement error handling
				try {
					invalidCredentials();
				} catch (TypeError) {}
				console.log(error);
			});
	};

	const checkPassword = () => {
		return password === retype;
	};

	const passwordsNotMatching = () => {
		document.getElementById('password').value = '';
		document.getElementById('password').classList.add('invalid-field');
		document.getElementById('retype').value = '';
		document.getElementById('retype').placeholder = "Passwords don't match";
		document.getElementById('retype').classList.add('invalid-field');
		setTimeout(() => {
			document.getElementById('password').classList.remove('invalid-field');
			document.getElementById('retype').classList.remove('invalid-field');
			document.getElementById('retype').placeholder = 'Retype Password';
		}, 1000);
	};

	const emailTaken = () => {
		document.getElementById('email').value = '';
		document.getElementById('email').placeholder = 'Email already taken';
		document.getElementById('email').classList.add('invalid-field');
		setTimeout(() => {
			document.getElementById('email').classList.remove('invalid-field');
			document.getElementById('email').placeholder = 'Email';
		}, 2000);
	};

	const invalidCredentials = () => {
		document.getElementById('login-part')?.classList.add('text-red-500');
		document.getElementById('login-part').innerText = 'Invalid credentials';

		setTimeout(() => {
			document.getElementById('login-part')?.classList.remove('text-red-500');
			document.getElementById('login-part').innerText = 'Sign in';
		}, 2000);
	};

	return (
		<div className='login-wrapper'>
			<div className='container' id='container'>
				<div className='form-container sign-up-container'>
					{/*  */}
					{/* REGISTER FORM */}
					<form action='#' method='POST' onSubmit={handleRegister}>
						<h1 className='text-2xl'>Create Account</h1>
						<input
							type='text'
							placeholder='First Name'
							name='firstName'
							onChange={onChangeFirstName}
							className='rounded-3xl'
						/>
						<input
							type='text'
							placeholder='Last Name'
							name='lastName'
							onChange={onChangeLastName}
							className='rounded-3xl'
						/>
						<input
							type='email'
							placeholder='Email'
							name='email'
							id='email'
							pattern='[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$'
							title='Enter a valid email address'
							onChange={onChangeEmail}
							className='rounded-3xl'
						/>
						<input
							type='password'
							placeholder='Password'
							name='password'
							id='password'
							pattern='(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'
							title='Must contain at least one number, one uppercase and lowercase letter, and at least 8 characters'
							onChange={onChangePassword}
							className='rounded-3xl'
						/>
						<input
							type='password'
							placeholder='Retype Password'
							name='retype'
							id='retype'
							onChange={onChangeRetype}
							className='rounded-3xl'
						/>
						<button type='submit'>Sign Up</button>
					</form>

					{/*  */}
					{/* END REGISTER FORM */}
				</div>
				<div className='form-container sign-in-container'>
					{/*  */}
					{/* LOGIN FORM */}
					<form action='#' method='POST' onSubmit={handleLogin}>
						<h1 className='text-2xl' id='login-part'>
							Sign in
						</h1>

						<input
							type='email'
							placeholder='Email'
							name='email'
							onChange={onChangeEmail}
							className='rounded-3xl'
						/>
						<input
							type='password'
							placeholder='Password'
							name='password'
							onChange={onChangePassword}
							className='rounded-3xl'
						/>
						<button type='submit'>Sign In</button>
					</form>
					{/*  */}
					{/* END LOGIN FORM */}
				</div>
				<div className='overlay-container'>
					<div className='overlay'>
						<div className='overlay-panel overlay-left'>
							<h1 className='text-3xl'>Welcome Back!</h1>
							<p>To keep connected with us please login with your personal info</p>
							<button className='ghost' id='signIn'>
								Sign In
							</button>
						</div>
						<div className='overlay-panel overlay-right'>
							<h1 className='text-3xl'>Hello, Friend!</h1>
							<p>Enter your personal details and start your journey with us</p>
							<button className='ghost' id='signUp'>
								Sign Up
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default Login;
