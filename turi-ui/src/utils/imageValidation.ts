const formats = ['image/jpeg', 'image/jpg', 'image/png']
const maxSizeInMB = 2

export const imageValidation = (file: File): void => {
    if (!formats.includes(file.type) || file.size > maxSizeInMB * 1024 * 1024) {
        return
    }
}
