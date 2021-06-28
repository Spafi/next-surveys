import { downloadImageUrl } from '../../BASE_URL';
import QuestionBody from './QuestionBody';
const Question = ({
	id,
	type,
	title,
	options,
	responses,
	required,
	imageName,
}) => {
	return (
		<div
			id={id}
			className='bg-white w-3/4 max-w-2xl self-center rounded-xl p-4 shadow-md grid grid-cols-12 gap-4'
		>
			<div className='col-span-11'>
				<p className='text-xl rounded-xl mb-4 w-full p-1 pl-4'>{title}</p>
				<QuestionBody
					type={type}
					options={options}
					responses={responses}
					questionId={id}
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
