import { Redirect } from 'react-router';
import { isAuthenticated, AuthHeader } from '../Utils';
import axios from 'axios';
import React, { useState, useEffect } from 'react';
import {useHistory} from 'react-router-dom';
import Question from './Question';
import { ReactComponent as Copy } from '../surveyCreation/icons/copy.svg';
import { ReactComponent as Delete } from '../surveyCreation/icons/trash.svg';

import { surveyUrl, frontendResponseUrl } from '../../BASE_URL';

const Results = ({ match }) => {
	const [title, setTitle] = useState('');
	const [description, setDescription] = useState('');
	const [questions, setQuestions] = useState([]);
	const surveyId = match.params.surveyId;
	let history = useHistory();

	const getSurvey = async () => {
		await axios
			.get(`${surveyUrl}/${surveyId}/responses`, { headers: AuthHeader() })
			.then((response) => {
				setTitle(response.data.title);
				setDescription(response.data.description);
				setQuestions(response.data.questions);
			})
			.catch((error) => {
				console.log(error.response);
				let response = error.response.data.message;
				if (response.startsWith('No Survey found with ID')) {
					document.getElementById('title').innerText = response;
				}
			});
	};

	const deleteSurvey = () => {
		axios.delete(`${surveyUrl}/${surveyId}`, { headers: AuthHeader() }).then(() => history.push('/user'))
		
	};

	useEffect(() => {
		getSurvey();
		// eslint-disable-next-line
	}, []);

	const copyUrl = () => {
		navigator.clipboard.writeText(`${frontendResponseUrl}/${surveyId}`);
	};

	if (!isAuthenticated()) return <Redirect to='/' />;
	return (
		<div className='bg-purple-100 grid w-screen h-screen pt-16 grid-cols-6 scrollbar-thin scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full relative'>
			{/* Left Buttons */}
			<div className='fixed h-screen w-1/6 col-span-1 p-8 flex flex-col justify-between border-gradient-t-pink-orange border-r-2 min-w-min'>
				<div className='flex flex-col gap-4'>
					{questions.map((question) => (
						<a
							href={`#${question.questionId}`}
							key={question.questionId}
							className='bg-white h-12 cursor-pointer shadow-md hover:shadow-xl flex items-center rounded-3xl pl-6 transition duration-300 ease-in-out transform hover:scale-110 hover:-translate-y-1 scrollbar-thin scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full'
						>
							<div className='h-6'>{question.title}</div>
						</a>
					))}
				</div>
			</div>
			{/* END Left Buttons */}
			<div
				id='surveyForm'
				className='col-span-4 col-start-2 p-12 flex flex-col gap-4'
			>
				<div className='bg-white w-3/4 max-w-2xl self-center rounded-xl p-4 shadow-md '>
					<p
						className='text-3xl w-full text-center mb-4 rounded-xl py-2 '
						id='title'
					>
						{title}
					</p>
					<p className='text-lg rounded-xl px-4 w-full text-gray-700'>
						{description}
					</p>
				</div>
				{questions.map((question) => (
					<Question
						id={question.questionId}
						key={question.questionId}
						type={question.type}
						title={question.title}
						responses={question.questionResponses}
						options={question.options}
						required={question.required}
						imageName={question.imageName}
					/>
				))}
			</div>
			<div className='fixed right-0 h-screen w-52 p-8 flex flex-col justify-between'>
				<div className='flex flex-col gap-4'>
					<div
						onClick={copyUrl}
						className='bg-white h-12 cursor-pointer text-lg shadow-md hover:shadow-xl flex items-center rounded-3xl pl-4 transition duration-300 ease-in-out transform hover:scale-110 hover:-translate-y-1'
					>
						<Copy className='h-12 pr-4 w-12 ' />
						<p className='-ml-2'>Copy URL</p>
					</div>
					<div
						onClick={deleteSurvey}
						className='bg-white h-12 cursor-pointer text-lg shadow-md hover:shadow-xl flex items-center rounded-3xl pl-4 transition duration-300 ease-in-out transform hover:scale-110 hover:-translate-y-1'
					>
						<Delete className='h-12 pr-4 w-12 ' />
						<p className='-ml-2'>Delete</p>
					</div>
				</div>
			</div>
		</div>
	);
};

export default Results;
