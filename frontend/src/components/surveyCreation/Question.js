import { useState } from 'react';
import QuestionBody from './QuestionBody';
import UUIDv4 from '../Utils';
import { ReactComponent as Trash } from './icons/trash.svg';
import { ReactComponent as Image } from './icons/image.svg';
const Question = ({
	id,
	type,
	onRemove,
	onChangeQuestionTitle,
	updateQuestionOptions,
	updateQuestionSelect,
	updateRequired,
	onChangeImage,
}) => {
	const [options, setOptions] = useState([{ id: UUIDv4(), value: '' }]);
	const [select, setSelect] = useState({ ratingFrom: '0', ratingTo: '5' });
	const [required, setRequired] = useState(false);
	const [image, setImage] = useState(null);

	if (type === 'rating') updateQuestionSelect(id, select);

	const removeOption = (optionId) => {
		if (options.length > 1) {
			let newOptions = options.filter((option) => option.id !== optionId);
			setOptions(newOptions);
			updateQuestionOptions(id, newOptions);
		}
	};

	const handleInputChange = (e, inputId) => {
		const input = e.target.value;
		for (let option of options) {
			if (option.id === inputId) option.value = input;
		}
		updateQuestionOptions(id, options);
	};

	const handleSelectChange = (e, selectId) => {
		select[selectId] = e.target.value;
		setSelect({ ...select });
		updateQuestionSelect(id, select);
	};

	const handleRequiredChange = (e) => {
		const requiredCheckbox = e.target.checked;
		setRequired(requiredCheckbox);
		updateRequired(e, id);
	};

	const handleImageChange = (e, id) => {
		const image = e.target.files[0];
		setImage(image);
		onChangeImage(image, id);
	};

	return (
		<div className='bg-white w-3/4 max-w-2xl self-center rounded-xl p-4 shadow-md focus-within:shadow-xl focus-within:bg-gray-50 grid grid-cols-12 gap-4'>
			<div className='col-span-11'>
				<input
					onChange={(e) => onChangeQuestionTitle(e, id)}
					type='text'
					placeholder='Question'
					className='text-xl outline-none border rounded-xl mb-4 w-full p-1 focus-within:border-m-pink'
				/>
				<QuestionBody
					type={type}
					options={options}
					select={select}
					setOptions={setOptions}
					removeOption={removeOption}
					updateQuestionSelect={updateQuestionSelect}
					handleInputChange={handleInputChange}
					handleSelectChange={handleSelectChange}
				/>
				<div className={`flex flex-row relative ${image !== null ? '' : 'hidden'}`}>
					<img id={`${id}-image`} src='#' alt='question' className={`p-6 `} />
					{/* <Trash
						className=' w-6 h-6 cursor-pointer absolute top-1/2 -right-2'
						onClick={() => handleImageChange(null, id)}
					/> */}
				</div>
			</div>
			<div className='flex flex-col gap-2 justify-between items-end pr-3'>
				<div className='flex flex-col gap-2 pt-1'>
					<Trash className='w-6 h-6 cursor-pointer' onClick={() => onRemove(id)} />{' '}
					<input
						type='file'
						accept='image/*'
						id={`${id}-file-upload`}
						hidden
						onChange={(e) => handleImageChange(e, id)}
					/>
					<label htmlFor={`${id}-file-upload`} className='w-6 h-6'>
						<Image className='w-6 h-6 cursor-pointer' />
					</label>
				</div>
				<div className='flex items-center '>
					<label htmlFor='required' className='pr-3 text-sm'>
						Required
					</label>
					<input
						type='checkbox'
						name='required'
						defaultChecked={required}
						className='h-6 w-6 cursor-pointer'
						onChange={(e) => handleRequiredChange(e)}
					/>
				</div>
			</div>
		</div>
	);
};

export default Question;
