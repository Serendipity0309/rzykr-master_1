// 查询材料列表
export function permission(permiss) {
	let permission = uni.getStorageSync("permission")
	for (let i = 0; i < permission.length; i++) {
		if (permission[0].permission === permiss) return true
	}
	return false
}