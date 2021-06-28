import { useState, useEffect } from 'react';
import axios from 'axios';
import { ReactComponent as Upload } from '../surveyCreation/icons/upload.svg';
import Question from './Question';
import { isAuthenticated, AuthHeader } from '../Utils';
import { surveyUrl } from '../../BASE_URL';
import { responseUrl } from '../../BASE_URL';
import { Redirect } from 'react-router';
import { useHistory } from 'react-router-dom';

const SurveyResponse = ({ match }) => {
	const [title, setTitle] = useState('');
	const [description, setDescription] = useState('');
	const [questions, setQuestions] = useState([]);
	const surveyId = match.params.surveyId;
	let history = useHistory();

	const updateQuestionResponses = (questionId, responses) => {
		for (let question of questions)
			if (question.questionId === questionId) question.responses = responses;
	};

	const getSurvey = async () => {
		await axios
			.get(`${surveyUrl}/${surveyId}`, { headers: AuthHeader() })
			.then((response) => {
				setTitle(response.data.title);
				setDescription(response.data.description);
				setQuestions(response.data.questions);
			})
			.catch((error) => {
				console.log(error);
			});
	};

	const checkRequiredResponses = () => {
		let requiredCompleted = true;
		questions.forEach((question) => {
			if (question.required && question.responses === undefined) {
				requiredCompleted = false;
			}
			if (question.type === 'multipleChoice' && question.responses !== undefined) {
				if (question.responses[1]?.value === undefined) {
					requiredCompleted = false;
				}
			}
		});
		return requiredCompleted;
	};
	const handleRespond = () => {
		const response = { appUserId: localStorage.getItem('userId') };
		let responsesArray = [];
		questions.forEach((question) => {
			responsesArray.push(question.responses?.filter((r) => r.value));
		});
		response.responses = responsesArray;
		return response;
	};

	useEffect(() => {
		getSurvey();
		// eslint-disable-next-line
	}, []);

	const sendResponse = () => {
		console.log(checkRequiredResponses());
		if (checkRequiredResponses()) {
			const responseBody = handleRespond();
			console.log(responseBody);
			axios
				.post(`${responseUrl}/${surveyId}`, responseBody, { headers: AuthHeader() })
				.then((response) => {
					// console.log(response);
					history.push('/user');
				})
				.catch((error) => {
					console.log(error);
				});
			return;
		}
	};
	if (!isAuthenticated()) return <Redirect to='/' />;
	return (
		<div className='bg-purple-100 grid w-screen h-screen pt-16 grid-cols-6 scrollbar-thin scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full relative'>
			<div
				id='surveyForm'
				className='col-span-4 col-start-2 p-12 flex flex-col gap-4'
			>
				<div className='bg-white w-3/4 max-w-2xl self-center rounded-xl p-4 shadow-md '>
					<p className='text-3xl w-full text-center mb-4 rounded-xl py-2 '>
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
						responses={question.responses}
						options={question.options}
						required={question.required}
						imageName={question.imageName}
						updateQuestionResponses={updateQuestionResponses}
					/>
				))}
			</div>
			<div className='fixed right-0 h-screen w-52 p-8 flex flex-col justify-between'>
				{questions.length > 0 && (
					<div className='flex flex-col gap-4'>
						<div
							onClick={() => sendResponse()}
							className='bg-white h-12 cursor-pointer text-lg shadow-md hover:shadow-xl flex items-center rounded-3xl pl-6 transition duration-300 ease-in-out transform hover:scale-110 hover:-translate-y-1'
						>
							<Upload className='h-12 pr-4 w-12' />
							<p className='-ml-2'>Respond</p>
						</div>
					</div>
				)}
			</div>
		</div>
	);
};

export default SurveyResponse;
