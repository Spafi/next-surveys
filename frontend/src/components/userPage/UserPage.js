import { useState, useEffect } from 'react';
import { Redirect } from 'react-router';

import axios from 'axios';

import { userSurveysUrl } from '../../BASE_URL';
import SurveyCard from './SurveyCard';
import { isAuthenticated, AuthHeader } from '../Utils';

const UserPage = () => {
	const [surveys, setSurveys] = useState([]);
	const firstName = localStorage.getItem('firstName');

	const getSurvey = async () => {
		const userId = localStorage.getItem('userId');
		await axios
			.get(`${userSurveysUrl}/${userId}`, { headers: AuthHeader() })
			.then((response) => {
				setSurveys(response.data);
			})
			.catch((error) => {
				console.log(error);
			});
	};

	useEffect(() => {
		getSurvey();
		// eslint-disable-next-line
	}, []);

	if (!isAuthenticated()) return <Redirect to='/' />;
	return (
		<div className='bg-purple-100 grid w-screen h-screen pt-16 scrollbar-thin justify-center scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full '>
			{surveys.length === 0 && (
				<div className='text-center text-xl pt-12 text-gray-700'>
					<p>Hello, {firstName}! </p> 
					<p>It seems that you haven't created any survey.</p>
				</div>
			)}
			<div className='grid grid-cols-4 gap-4 items-stretch p-4 pt-12 border-red-700 grid-rows-5'>
				{surveys.map((survey) => (
					<SurveyCard
						key={survey.surveyId}
						id={survey.surveyId}
						title={survey.title}
						description={survey.description}
						questionsCount={survey.questions.length}
					/>
				))}
			</div>
		</div>
	);
};

export default UserPage;