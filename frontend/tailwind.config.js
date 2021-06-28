module.exports = {
	purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
	darkMode: false, // or 'media' or 'class'
	theme: {
		linearBorderGradients: {
			directions: {
				// defaults to these values
				t: 'to top',
				tr: 'to top right',
				r: 'to right',
				br: 'to bottom right',
				b: 'to bottom',
				bl: 'to bottom left',
				l: 'to left',
				tl: 'to top left',
			},
			colors: {
				// defaults to {}
				// red: '#f00',
				'pink-orange': ['#ff416c', '#ff4b2b'],
				// 'red-green-blue': ['#f00', '#0f0', '#00f'],
				// 'black-white-with-stops': ['#000', '#000 45%', '#fff 55%', '#fff'],
			},
			extend: {
				width: { 128: '64rem' },
			},
		},
		repeatingLinearBorderGradients: (theme) => ({
			directions: theme('linearBorderGradients.directions'), // defaults to the same values as linearBorderGradients’ directions
			colors: theme('linearBorderGradients.colors'), // defaults to {}
			lengths: {
				// defaults to {}
				sm: '25px',
				md: '50px',
				lg: '100px',
			},
		}),
		extend: {
			fontFamily: {
				montserrat: ['"Montserrat"', 'cursive'],
			},
			zIndex: {
				'-10': '-10',
			},
			colors: {
				transparent: 'transparent',
				current: 'currentColor',
				'm-gray': {
					DEFAULT: '#2b2d42',
					light: '#8d99ae',
					lighter: '#dce1e3',
				},
				'm-red': {
					darker: '#d90429',
					DEFAULT: '#ef233c',
					lighter: '#fffbff',
				},
				'm-pink': {
					DEFAULT: '#ff416c',
				},
				'm-orange': {
					DEFAULT: '#ff4b2b',
				},
			},
		},
	},
	variants: {
		linearBorderGradients: ['responsive'], // defaults to ['responsive']
		repeatingLinearBorderGradients: ['responsive'], // defaults to ['responsive']
		scrollbar: ['rounded'],
	},
	plugins: [
		require('tailwindcss-border-gradients')(),
		require('tailwind-scrollbar'),
	],
};
