const state = {
	printlist: [],
	id: '',
}
const mutations = {
	SET_printdata: state => {
		this.printlist = state
	},
	GET_printdata: () => {
		return this.printlist
	}
}
export default {
	state,
	mutations
}
