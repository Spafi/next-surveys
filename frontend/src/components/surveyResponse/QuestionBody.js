import ReactStars from 'react-stars';
const QuestionBody = ({
	type,
	options,
	responses,
	questionId,
	handleInputChange,
	handleCheckboxChange,
	handleRadioChange,
	clearRadioSelection,
	isChecked,
}) => {
	let questionBody;
	switch (type) {
		case 'shortAnswer':
			questionBody = (
				<div className='pl-2'>
					<input
						id={questionId}
						type='text'
						placeholder='Short answer text'
						className='w-full outline-none rounded-xl pl-2 border focus-within:border-m-pink'
						onChange={(e) => handleInputChange(e, questionId)}
					/>
				</div>
			);
			break;

		case 'longAnswer':
			questionBody = (
				<div className='pl-2 outline-none'>
					<textarea
						id={questionId}
						type='text'
						placeholder='Long answer text'
						className='w-full border rounded-xl pl-2 outline-none focus-within:border-m-pink'
						onChange={(e) => handleInputChange(e, questionId)}
					/>
				</div>
			);
			break;

		case 'rating':
			let boundaries = [options[0].value, options[1].value];
			let from = Math.min(...boundaries);
			let to = Math.max(...boundaries);
			const ratingChanged = (newRating) => {
				handleInputChange(newRating, questionId, from);
			};
			questionBody = (
				<div className='pl-2 outline-none'>
					<div className='flex items-center justify-center'>
						<span className='px-4 text-2xl'>{from}</span>

						{from === 0 && (
							<ReactStars
								count={to + 1}
								size={36}
								half={false}
								onChange={ratingChanged}
							/>
						)}
						{from === 1 && (
							<ReactStars count={to} size={36} half={false} onChange={ratingChanged} />
						)}
						<span className='px-4 text-2xl'>{to}</span>
					</div>
				</div>
			);
			break;

		case 'multipleChoice':
			questionBody = (
				<>
					<div className='pl-2 flex flex-col gap-2'>
						{options.map((option) => (
							<div key={option.optionId} className='flex flex-row'>
								<input
									type='checkbox'
									name='checkbox'
									className='h-5 w-5'
									onChange={() => handleCheckboxChange(option.value)}
								/>
								<p className='ml-2 outline-none focus-within:border-m-red w-4/5'>
									{option.value}
								</p>
							</div>
						))}
					</div>
				</>
			);
			break;

		case 'singleChoice':
			questionBody = (
				<>
					<div className='pl-2 flex flex-col gap-2 relative'>
						{options.map((option) => (
							<div key={option.optionId} className='flex flex-row'>
								<input
									id={option.optionId}
									type='radio'
									name={`radio-${questionId}`}
									className='h-5 w-5'
									onChange={() => handleRadioChange(option.value)}
								/>
								<label htmlFor={`radio-${questionId}`} className='ml-2 w-4/5'>
									{option.value}
								</label>
							</div>
						))}
						{isChecked && (
							<div
								className='font-bold text-gray-600 cursor-pointer flex justify-end absolute right-0 bottom-0'
								onClick={() => clearRadioSelection(`radio-${questionId}`)}
							>
								Clear selection
							</div>
						)}
					</div>
				</>
			);
			break;

		default:
			questionBody = '';
	}
	return questionBody;
};

export default QuestionBody;
