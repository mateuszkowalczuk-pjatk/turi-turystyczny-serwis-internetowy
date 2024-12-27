import { imageService } from '../../../../services/imageService.ts'
import styles from './Image.module.css'

interface Props {
    id?: number
    path?: string
    alt?: string
    removeImage?: (imageId: number) => void
    file?: File
    removeFile?: (file: File) => void
    onlyDisplay?: boolean
}

const ImagePanel = ({ id, path, alt = 'Image', removeImage, file, removeFile, onlyDisplay }: Props) => {
    const handleImageDelete = async () => {
        if (removeFile && file) removeFile(file)
        else if (id != null && removeImage) {
            await imageService.deleteById(id)
            removeImage(id)
        }
    }

    return (
        <div className={styles.panel}>
            {path && (
                <img
                    src={path}
                    alt={alt}
                    className={styles.image}
                />
            )}
            {file && (
                <img
                    src={URL.createObjectURL(file)}
                    alt={alt}
                    className={styles.image}
                />
            )}
            {!onlyDisplay && (
                <button
                    className={styles.delete}
                    onClick={handleImageDelete}
                    aria-label="Delete image"
                    type="button"
                >
                    ✕
                </button>
            )}
        </div>
    )
}

export default ImagePanel
