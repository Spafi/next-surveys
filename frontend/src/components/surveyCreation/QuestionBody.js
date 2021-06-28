import { ReactComponent as Add } from './icons/add.svg';
import UUIDv4 from '../Utils';




const QuestionBody = ({
	type,
	options,
	select,
	setOptions,
	handleInputChange,
	handleSelectChange,
	removeOption,
}) => {
	let questionBody;
	switch (type) {
		case 'shortAnswer':
			questionBody = (
				<div className='pl-2'>
					<input
						type='text'
						placeholder='Short answer text'
						className='w-5/6 outline-none rounded-xl pl-2'
						disabled
					/>
				</div>
			);
			break;

		case 'longAnswer':
			questionBody = (
				<div className='pl-2 outline-none'>
					<input
						type='text'
						placeholder='Long answer text'
						className='w-5/6 outline-none rounded-xl pl-2'
						disabled
					/>
				</div>
			);
			break;

		case 'rating':
			questionBody = (
				<div className='pl-2 outline-none'>
					<select
						name='ratingFrom'
						id='ratingFrom'
						className='p-2 mr-8 rounded-xl outline-none border focus:border-m-red w-16'
						value={select.ratingFrom}
						onChange={(e) => handleSelectChange(e, 'ratingFrom')}
					>
						<option value='0'>0</option>
						<option value='1'>1</option>
					</select>
					<select
						name='ratingTo'
						id='ratingTo'
						className='p-2 rounded-xl outline-none border focus:border-m-red w-16'
						value={select.ratingTo}
						onChange={(e) => handleSelectChange(e, 'ratingTo')}
					>
						<option value='2'>2</option>
						<option value='3'>3</option>
						<option value='4'>4</option>
						<option value='5'>5</option>
						<option value='6'>6</option>
						<option value='7'>7</option>
						<option value='8'>8</option>
						<option value='9'>9</option>
						<option value='10'>10</option>
					</select>
				</div>
			);		
			break;

		case 'multipleChoice':
			questionBody = (
				<>
					<div className='pl-2 flex flex-col gap-2'>
						{options.map((option) => (
							<div key={option.id} className='flex flex-row'>
								<input type='checkbox' name='checkbox' className='h-5 w-5' disabled />
								<input
									type='text'
									placeholder={`Option ${options.indexOf(option) + 1}`}
									className='ml-2 outline-none border-b focus-within:border-m-red w-4/5'
									onChange={(e) => handleInputChange(e, option.id)}
								/>
								{options.length > 1 && (
									<div
										onClick={() => removeOption(option.id)}
										className='font-bold text-3xl leading-3 cursor-pointer '
									>
										&times;
									</div>
								)}
							</div>
							
						))}
					</div>
					<div
						onClick={() => {
							setOptions([...options, { id: UUIDv4(), value: '' }]);
						}}
						className='flex items-center mt-4 cursor-pointer border w-min p-1 px-2 rounded-xl hover:border-m-red'
					>
						<Add className='w-4 h-4 border-2 border-transparent mr-2' />
						<p>Add</p>
					</div>
				</>
			);
			break;

		case 'singleChoice':
			questionBody = (
				<>
					<div className='pl-2 flex flex-col gap-2'>
						{options.map((option) => (
							<div key={option.id} className='flex flex-row'>
								<input type='radio' name='radio' className='h-5 w-5' disabled />
								<input
									type='text'
									placeholder={`Option ${options.indexOf(option) + 1}`}
									className='ml-2 outline-none border-b focus-within:border-m-red w-4/5'
									onChange={(e) => handleInputChange(e, option.id)}
								/>
								{options.length > 1 && (
									<div
										onClick={() => removeOption(option.id)}
										className='font-bold text-3xl leading-3 cursor-pointer '
									>
										&times;
									</div>
								)}
							</div>
						))}
					</div>
					<div
						onClick={() => {
							setOptions([...options, { id: UUIDv4(), value: '' }]);
						}}
						className='flex items-center mt-4 cursor-pointer border w-min p-1 px-2 rounded-xl hover:border-m-red'
					>
						<Add className='w-4 h-4 border-2 border-transparent mr-2' />
						<p>Add</p>
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
