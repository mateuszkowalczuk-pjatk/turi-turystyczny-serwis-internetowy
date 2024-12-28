import { useNavigate } from 'react-router-dom'
import styles from './Return.module.css'

interface TourismReturnProps {
    url: string
    text: string
}

const Return = ({ url, text }: TourismReturnProps) => {
    const navigate = useNavigate()

    return (
        <div
            className={styles.return}
            onClick={() => navigate(url)}
            role="button"
            tabIndex={0}
        >
            {text}
        </div>
    )
}

export default Return
