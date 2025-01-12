import NotFoundHeader from '../NotFoundHeader'
import NotFoundTitle from '../NotFoundTitle'
import NotFoundDescription from '../NotFoundDescription'
import NotFoundButton from '../NotFoundButton'
import styles from './NotFoundPanel.module.css'

const NotFoundPanel = () => {
    return (
        <div className={styles.panel}>
            <NotFoundHeader />
            <NotFoundTitle />
            <NotFoundDescription />
            <NotFoundButton />
        </div>
    )
}

export default NotFoundPanel
