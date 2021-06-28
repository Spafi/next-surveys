import { useState } from 'react';
import { downloadImageUrl } from '../../BASE_URL';
import QuestionBody from './QuestionBody';
const Question = ({
	id,
	type,
	title,
	options,
	required,
	updateQuestionResponses,
	imageName,
}) => {
	const [responses, setResponses] = useState([{ questionId: id }]);
	const [isChecked, setIsChecked] = useState(false);


	const handleInputChange = (e, questionId, from = null) => {
		let input;
		switch (typeof e) {
			case 'number':
				from === 0 ? (input = e - 1) : (input = e);
				break;
			default:
				input = e.target.value;
		}
		for (let response of responses) {
			if (response.questionId === questionId) response.value = input;
		}
		updateQuestionResponses(id, responses);
	};

	const handleRadioChange = (optionId) => {
		responses.splice(0, responses.length);
		let selectedResponse = { questionId: id, value: optionId };
		responses.push(selectedResponse);
		updateQuestionResponses(id, responses);
		checkIfRadioIsChecked();
	};

	const clearRadioSelection = (radioName) => {
		let element = document.getElementsByName(radioName);
		for (let i = 0; i < element.length; i++) element[i].checked = false;
		updateQuestionResponses(id, [{ questionId: id }]);
		setIsChecked(false);
	};

	const handleCheckboxChange = (optionId) => {
		let selectedResponse = { questionId: id, value: optionId };
		if (!responses.some((r) => r.value === selectedResponse.value))
			responses.push(selectedResponse);
		else responses.pop(selectedResponse);
		updateQuestionResponses(id, responses);
	};

	const checkIfRadioIsChecked = () => {
		if (responses[0].value !== null) setIsChecked(true);
	};

	return (
		<div className='bg-white w-3/4 max-w-2xl self-center rounded-xl p-4 shadow-md grid grid-cols-12 gap-4'>
			<div className='col-span-11'>
				<p className='text-xl rounded-xl mb-4 w-full p-1'>{title}</p>
				<QuestionBody
					type={type}
					options={options}
					responses={responses}
					setResponses={setResponses}
					questionId={id}
					handleInputChange={handleInputChange}
					handleCheckboxChange={handleCheckboxChange}
					handleRadioChange={handleRadioChange}
					clearRadioSelection={clearRadioSelection}
					isChecked={isChecked}
				/>
				<div
					className={`flex flex-row relative ${imageName !== null ? '' : 'hidden'}`}
				>
					<img
						id={`${id}-image`}
						src={imageName !== null ? `${downloadImageUrl}/${imageName}` : '#'}
						alt='question'
						className={`p-6 `}
					/>
				</div>
			</div>
			<div className='flex flex-col gap-2 justify-between items-end'>
				<div className='flex items-center '>
					{required === true && <p className='text-xs text-red-600'>Required</p>}
				</div>
			</div>
		</div>
	);
};

export default Question;
