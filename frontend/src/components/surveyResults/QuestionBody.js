import ReactStars from 'react-stars';
const QuestionBody = ({ type, options, responses, questionId }) => {
	let questionBody;
	switch (type) {
		case 'shortAnswer':
			questionBody = (
				<div className='pl-2'>
					{responses.map((response) => (
						<textarea
							key={response.responseId}
							defaultValue={response.value}
							className='w-full outline-none pl-2  my-1 scrollbar-thin scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full'
							rows={1}
						/>
					))}
				</div>
			);
			break;
		case 'longAnswer':
			questionBody = (
				<div className='pl-2'>
					{responses.map((response) => (
						<textarea
							key={response.responseId}
							defaultValue={response.value}
							className='w-full outline-none pl-2 rounded-xl border my-1 scrollbar-thin scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full'
							disabled
							rows={2}
						/>
					))}
				</div>
			);
			break;

		case 'rating':
			let boundaries = [options[0].value, options[1].value];
			let from = Math.min(...boundaries);
			let to = Math.max(...boundaries);
			questionBody = (
				<div className='pl-2 outline-none'>
					{responses.map((response) => (
						<div
							key={response.responseId}
							className='flex items-center justify-center'
						>
							<span className='px-4 text-2xl'>{from}</span>
							{from === 0 && (
								<ReactStars
									count={to + 1}
									size={36}
									edit={false}
									value={parseInt(response.value)}
								/>
							)}
							{from === 1 && (
								<ReactStars
									count={to}
									size={36}
									edit={false}
									value={parseInt(response.value)}
								/>
							)}
							<span className='px-4 text-2xl'>{to}</span>
						</div>
					))}
				</div>
			);
			break;

		case 'multipleChoice':
			questionBody = (
				<>
					<div className='pl-2 flex flex-col gap-2'>
						{responses.map((response) => (
							<div className='flex flex-row border-b' key={response.responseId}>
								<input
									type='checkbox'
									name={`checkbox-${response.responseId}`}
									defaultValue={response.value}
									className='h-5 w-5 '
									checked
									readOnly
								/>
								<label
									htmlFor={`checkbox-${response.responseId}`}
									className='ml-2 w-4/5'
								>
									{response.value}
								</label>
							</div>
						))}
					</div>
				</>
			);
			break;

		case 'singleChoice':
			questionBody = (
				<div className='pl-2 flex flex-col gap-2 relative'>
					{responses.map((response) => (
						<div className='flex flex-row border-b' key={response.responseId}>
							<input
								type='radio'
								name={`radio-${response.responseId}`}
								defaultValue={response.value}
								className='h-5 w-5 '
								checked
								readOnly
							/>
							<label htmlFor={`radio-${response.responseId}`} className='ml-2 w-4/5'>
								{response.value}
							</label>
						</div>
					))}
				</div>
			);
			break;

		default:
			questionBody = '';
	}
	return questionBody;
};

export default QuestionBody;
