import { imageService } from '../../../../services/imageService.ts'
import styles from './Image.module.css'

interface Props {
    id?: number
    path: string
    alt?: string
    removeImage: (imageId: number) => void
}

const ImagePanel = ({ id, path, alt = 'Image', removeImage }: Props) => {
    const handleImageDelete = async () => {
        if (id != null) {
            await imageService.deleteById(id)
            removeImage(id)
        }
    }

    return (
        <div className={styles.panel}>
            <img
                src={path}
                alt={alt}
                className={styles.image}
            />
            <button
                className={styles.delete}
                onClick={handleImageDelete}
                aria-label="Delete image"
            >
                ✕
            </button>
        </div>
    )
}

export default ImagePanel
