import { Link } from 'react-router-dom';
import { frontendResponseUrl } from '../../BASE_URL';

const surveyCard = ({ id, title, description, questionsCount }) => {
	return (
		<Link
			to={`/results/${id}`}
			className='bg-white rounded-l-xl px-4 shadow-md cursor-default max-h-36 w-72 scrollbar-thin scrollbar-thumb-red-300 scrollbar-track-transparent overflow-y-scroll scrollbar-thumb-rounded-full scrollbar-track-rounded-full'
		>
			<p className='text-2xl w-full pt-4'>{title}</p>
			<p className='text-md w-full text-gray-700'>{description}</p>
			<p className='text-xs py-2'>
				{questionsCount} {questionsCount > 1 ? 'questions' : 'question'}
			</p>
			<p
				className='text-xs py-2 cursor-pointer'
				onClick={(e) => {
					e.preventDefault();
					navigator.clipboard.writeText(`${frontendResponseUrl}/${id}`);
					e.target.textContent = 'Copied!'
					setTimeout(()=> {e.target.textContent = 'Click here to copy URL!';}, 1000)
				}}
			>
				Click here to copy URL
			</p>
		</Link>
	);
};

export default surveyCard;
